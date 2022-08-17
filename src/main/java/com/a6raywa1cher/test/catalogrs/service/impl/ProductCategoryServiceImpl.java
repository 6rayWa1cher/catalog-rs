package com.a6raywa1cher.test.catalogrs.service.impl;

import com.a6raywa1cher.test.catalogrs.dao.ProductCategory;
import com.a6raywa1cher.test.catalogrs.dao.repo.ProductCategoryRepository;
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

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository repository;
    private final DtoMapper mapper;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository,
                                      DtoMapper mapper) {
        this.repository = repository;
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

        assertProductCategoryTitleAvailable(productCategory.getTitle(), dto.getTitle());

        mapper.copyAll(dto, productCategory);

        ProductCategory saved = repository.save(productCategory);

        return mapper.map(saved);
    }

    @Override
    public ProductCategoryDto patch(Long id, ProductCategoryDto dto) {
        ProductCategory productCategory = getEntityById(id);

        assertProductCategoryTitleAvailable(productCategory.getTitle(), dto.getTitle());

        mapper.copyNotNull(dto, productCategory);

        ProductCategory saved = repository.save(productCategory);

        return mapper.map(saved);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresent((pc) -> {
                    // TODO: invoke delete for all Products
                    repository.delete(pc);
                });
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
}
