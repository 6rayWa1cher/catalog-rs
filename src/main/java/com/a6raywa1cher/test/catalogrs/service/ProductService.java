package com.a6raywa1cher.test.catalogrs.service;

import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import com.a6raywa1cher.test.catalogrs.dto.ShortProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService extends CrudService<ProductDto, Long> {
    Page<ShortProductDto> getPageByFilter(ProductQueryDto dto, Pageable pageable);

    ProductDto uploadImage(Long id, MultipartFile file);
}
