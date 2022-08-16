package com.a6raywa1cher.test.catalogrs.service;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {
    ProductCategoryDto getById(long id);

    Page<ProductCategoryDto> getPage(Pageable pageable);

    ProductCategoryDto create(ProductCategoryDto dto);

    ProductCategoryDto edit(long id, ProductCategoryDto dto);

    ProductCategoryDto patch(long id, ProductCategoryDto dto);

    void delete(long id);
}
