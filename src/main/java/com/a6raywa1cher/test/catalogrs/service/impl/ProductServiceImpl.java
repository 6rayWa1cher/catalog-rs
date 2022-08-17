package com.a6raywa1cher.test.catalogrs.service.impl;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dao.ProductCategory;
import com.a6raywa1cher.test.catalogrs.dao.repo.ProductCategoryRepository;
import com.a6raywa1cher.test.catalogrs.dao.repo.ProductRepository;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import com.a6raywa1cher.test.catalogrs.dto.ShortProductDto;
import com.a6raywa1cher.test.catalogrs.exception.EntityNotFoundAppException;
import com.a6raywa1cher.test.catalogrs.integration.FileStorage;
import com.a6raywa1cher.test.catalogrs.mapper.DtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductCategoryRepository categoryRepository;
    private final DtoMapper mapper;
    private final FileStorage fileStorage;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ProductCategoryRepository categoryRepository,
                              DtoMapper mapper, FileStorage fileStorage) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.fileStorage = fileStorage;
    }

    @Override
    public ProductDto getById(Long id) {
        return mapper.map(getEntityById(id));
    }

    @Override
    public Page<ProductDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public ProductDto create(ProductDto dto) {
        Product product = new Product();
        ProductCategory category = getCategoryById(dto.getCategory());

        mapper.copyAll(dto, product);

        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        Product saved = repository.save(product);

        return mapper.map(saved);
    }

    @Override
    public ProductDto edit(Long id, ProductDto dto) {
        Product product = getEntityById(id);
        ProductCategory category = getCategoryById(dto.getCategory());

        mapper.copyAll(dto, product);

        upsertProductIntoCategory(product, category);

        Product saved = repository.save(product);

        return mapper.map(saved);
    }

    @Override
    public ProductDto patch(Long id, ProductDto dto) {
        Product product = getEntityById(id);
        ProductCategory category = dto.getCategory() != null ?
                getCategoryById(dto.getCategory()) :
                product.getCategory();

        mapper.copyNotNull(dto, product);

        upsertProductIntoCategory(product, category);

        Product saved = repository.save(product);

        return mapper.map(saved);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresent((product) -> {
                    String imageUrl = product.getImageUrl();
                    if (imageUrl != null) {
                        try {
                            fileStorage.delete(imageUrl);
                        } catch (Exception e) {
                            log.error("Exception during Product{id=%d} deletion".formatted(product.getId()), e);
                        }
                    }
                    removeProductFromCategory(product);
                    repository.delete(product);
                });
    }

    private ProductCategory getCategoryById(Long id) {
        Objects.requireNonNull(id, "id must be not null");
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundAppException(id, ProductCategory.class));
    }

    private Product getEntityById(Long id) {
        Objects.requireNonNull(id, "id must be not null");
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundAppException(id, Product.class));
    }

    private void upsertProductIntoCategory(Product product, ProductCategory category) {
        ProductCategory prevCategory = product.getCategory();
        if (prevCategory != null && !prevCategory.equals(category)) {
            removeProductFromCategory(product);
        }
        product.setCategory(category);
        category.getProducts().add(product);
    }

    private void removeProductFromCategory(Product product) {
        product.getCategory().getProducts().remove(product);
        product.setCategory(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ShortProductDto> getPageByFilter(ProductQueryDto dto, Pageable pageable) {
        return repository.getFilteredPage(dto, pageable).map(mapper::mapToShort);
    }
}
