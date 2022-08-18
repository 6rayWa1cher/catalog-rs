package com.a6raywa1cher.test.catalogrs.service;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService extends CrudService<ProductCategoryDto, Long> {
    List<ProductCategoryDto> getAll();

    Optional<ProductCategoryDto> findByTitle(String title);
}
