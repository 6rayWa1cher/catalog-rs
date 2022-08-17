package com.a6raywa1cher.test.catalogrs.mapper;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.rest.dto.ProductCategoryRestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = MapperHelper.class, componentModel = "spring")
public interface RestDtoMapper {
    @Mapping(target = "id", ignore = true)
    ProductCategoryDto map(ProductCategoryRestDto dto);
}
