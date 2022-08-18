package com.a6raywa1cher.test.catalogrs.service.impl;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dao.ProductCategory;
import com.a6raywa1cher.test.catalogrs.dao.ProductStatus;
import com.a6raywa1cher.test.catalogrs.dao.repo.ProductCategoryRepository;
import com.a6raywa1cher.test.catalogrs.dao.repo.ProductRepository;
import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.exception.EntityNotFoundAppException;
import com.a6raywa1cher.test.catalogrs.exception.UniqueConstraintViolationAppException;
import com.a6raywa1cher.test.catalogrs.mapper.DtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository repository;
    private final ProductRepository productRepository;
    private final DtoMapper mapper;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository,
                                      ProductRepository productRepository, DtoMapper mapper) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductCategoryDto getById(Long id) {
        return mapper.map(getEntityById(id));
    }

    private ProductCategory getEntityById(Long id) {
        Objects.requireNonNull(id, "id must be not null");
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundAppException(id, ProductCategory.class));
    }

    @Override
    public Page<ProductCategoryDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public ProductCategoryDto create(ProductCategoryDto dto) {
        ProductCategory productCategory = new ProductCategory();

        mapper.copyAll(dto, productCategory);

        assertProductCategoryTitleAvailable(productCategory.getTitle());

        ProductCategory saved = repository.save(productCategory);

        return mapper.map(saved);
    }

    @Override
    public ProductCategoryDto update(Long id, ProductCategoryDto dto) {
        ProductCategory productCategory = getEntityById(id);

        String prevTitle = productCategory.getTitle();
        String newTitle = dto.getTitle();
        assertProductCategoryTitleAvailable(prevTitle, newTitle);

        mapper.copyAll(dto, productCategory);

        ProductCategory saved = repository.save(productCategory);

        return mapper.map(saved);
    }

    @Override
    public ProductCategoryDto patch(Long id, ProductCategoryDto dto) {
        ProductCategory productCategory = getEntityById(id);

        String prevTitle = productCategory.getTitle();
        String newTitle = dto.getTitle();
        assertProductCategoryTitleAvailable(prevTitle, newTitle);

        mapper.copyNotNull(dto, productCategory);

        ProductCategory saved = repository.save(productCategory);

        return mapper.map(saved);
    }

    @Override
    public void delete(Long id) {
        Optional<ProductCategory> byId = repository.findById(id);
        if (byId.isEmpty()) return;
        ProductCategory category = byId.get();
        unlinkProductsFromCategory(category);
        repository.delete(category);
    }

    private void unlinkProductsFromCategory(ProductCategory category) {
        for (Product p : category.getProducts()) {
            p.setCategory(null);
            p.setStatus(ProductStatus.INACTIVE);
        }
        productRepository.saveAll(category.getProducts());
    }

    private void assertProductCategoryTitleAvailable(String title) {
        if (repository.existsByTitle(title)) {
            throw new UniqueConstraintViolationAppException(Map.of("title", title));
        }
    }

    private void assertProductCategoryTitleAvailable(String prevTitle, String newTitle) {
        if (newTitle != null && !Objects.equals(prevTitle, newTitle)) {
            assertProductCategoryTitleAvailable(newTitle);
        }
    }

    @Override
    public List<ProductCategoryDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Optional<ProductCategoryDto> findByTitle(String title) {
        return repository.findByTitle(title).map(mapper::map);
    }
}
