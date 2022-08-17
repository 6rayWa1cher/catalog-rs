package com.a6raywa1cher.test.catalogrs.mapper;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.rest.dto.ProductCategoryRestDto;
import com.a6raywa1cher.test.catalogrs.rest.dto.ProductRestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = MapperHelper.class, componentModel = "spring")
public interface RestDtoMapper {
    @Mapping(target = "id", ignore = true)
    ProductCategoryDto map(ProductCategoryRestDto dto);

    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductDto map(ProductRestDto dto);
}
