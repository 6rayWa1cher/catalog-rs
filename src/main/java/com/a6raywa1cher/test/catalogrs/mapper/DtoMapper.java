package com.a6raywa1cher.test.catalogrs.mapper;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dao.ProductCategory;
import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.ShortProductDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(uses = MapperHelper.class, componentModel = "spring")
public interface DtoMapper {
    @Mapping(target = "category", source = "category.id")
    ProductDto map(Product product);

    @Mapping(target = "category", source = "category.id")
    ShortProductDto mapToShort(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void copyAll(ProductDto dto, @MappingTarget Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void copyNotNull(ProductDto dto, @MappingTarget Product product);

    ProductCategoryDto map(ProductCategory productCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void copyAll(ProductCategoryDto dto, @MappingTarget ProductCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void copyNotNull(ProductCategoryDto dto, @MappingTarget ProductCategory category);
}
