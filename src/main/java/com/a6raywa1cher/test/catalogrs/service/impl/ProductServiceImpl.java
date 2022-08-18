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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@Transactional
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
    public ProductDto update(Long id, ProductDto dto) {
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

        if (category != null) upsertProductIntoCategory(product, category);

        Product saved = repository.save(product);

        return mapper.map(saved);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresent((product) -> {
                    deleteImageFromProduct(product);
                    removeProductFromCategory(product);
                    repository.delete(product);
                });
    }

    private ProductCategory getCategoryById(Long id) {
        if (id == null) return null;
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundAppException(id, ProductCategory.class));
    }

    private Product getEntityById(Long id) {
        Objects.requireNonNull(id, "id must be not null");
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundAppException(id, Product.class));
    }

    private void upsertProductIntoCategory(Product product, ProductCategory category) {
        removeProductFromCategory(product);
        if (category == null) return;
        product.setCategory(category);
        category.getProducts().add(product);
    }

    private void removeProductFromCategory(Product product) {
        ProductCategory category = product.getCategory();
        if (category == null) return;
        category.getProducts().remove(product);
        product.setCategory(null);
    }

    private void deleteImageFromProduct(Product product) {
        String imageUrl = product.getImageUrl();
        if (imageUrl == null) return;
        deleteImageFromProduct(product, imageUrl);
        product.setImageUrl(null);
    }

    private void deleteImageFromProduct(Product product, String imageUrl) {
        if (imageUrl == null) return;
        try {
            fileStorage.delete(imageUrl);
        } catch (Exception e) {
            log.error(
                    "Exception during delete of image from Product{id=%d,imageUrl=%s}"
                            .formatted(product.getId(), imageUrl), e
            );
        }
    }

    @Override
    public Page<ShortProductDto> getPageByFilter(ProductQueryDto dto, Pageable pageable) {
        return repository.getFilteredPage(dto, pageable).map(mapper::mapToShort);
    }

    @Override
    public ProductDto uploadImage(Long id, MultipartFile file) {
        Product product = getEntityById(id);

        String prevImageUrl = product.getImageUrl();

        String imageUrl = fileStorage.upload(file, "image");
        product.setImageUrl(imageUrl);

        Product saved;
        try {
            saved = repository.saveAndFlush(product);
        } catch (Exception e) {
            deleteImageFromProduct(product, imageUrl);
            throw e;
        }

        deleteImageFromProduct(product, prevImageUrl); // delete prev file only when other steps completed successfully

        return mapper.map(saved);
    }
}
