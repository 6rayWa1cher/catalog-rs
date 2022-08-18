package com.a6raywa1cher.test.catalogrs.mapper;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductCategoryRequestDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductRequestDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductWithFileRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = MapperHelper.class, componentModel = "spring")
public interface RequestDtoMapper {
    @Mapping(target = "id", ignore = true)
    ProductCategoryDto map(ProductCategoryRequestDto dto);

    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductDto map(ProductRequestDto dto);

    @Mapping(target = "image", ignore = true)
    ProductWithFileRequestDto map(ProductDto dto);
}
