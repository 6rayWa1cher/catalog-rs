package com.a6raywa1cher.test.catalogrs.rest;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductCategoryRequestDto;
import com.a6raywa1cher.test.catalogrs.mapper.RequestDtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import com.a6raywa1cher.test.catalogrs.validation.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.validation.group.OnPatch;
import com.a6raywa1cher.test.catalogrs.validation.group.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/category")
@Tag(name = "product-category", description = "Product category operations")
public class ProductCategoryRestController {
    private final ProductCategoryService service;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductCategoryRestController(ProductCategoryService service, RequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Get a product category by id")
    public ProductCategoryDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping(path = "/", produces = "application/json")
    @Operation(summary = "Get a page of product categories")
    public Page<ProductCategoryDto> getPage(@ParameterObject Pageable pageable) {
        return service.getPage(pageable);
    }

    @PostMapping(path = "/", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a product category")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(mediaType = "application/json"))
    public ProductCategoryDto create(@RequestBody @Validated(OnCreate.class) ProductCategoryRequestDto dto) {
        return service.create(mapper.map(dto));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Update a product category")
    public ProductCategoryDto update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) ProductCategoryRequestDto dto) {
        return service.update(id, mapper.map(dto));
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Partially update a product category")
    public ProductCategoryDto patch(@PathVariable long id, @RequestBody @Validated(OnPatch.class) ProductCategoryRequestDto dto) {
        return service.patch(id, mapper.map(dto));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product category")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
