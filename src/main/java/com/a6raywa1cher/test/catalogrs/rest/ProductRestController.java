package com.a6raywa1cher.test.catalogrs.rest;

import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import com.a6raywa1cher.test.catalogrs.dto.ShortProductDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductRequestDto;
import com.a6raywa1cher.test.catalogrs.mapper.RequestDtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import com.a6raywa1cher.test.catalogrs.validation.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.validation.group.OnPatch;
import com.a6raywa1cher.test.catalogrs.validation.group.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/products")
@Tag(name = "product", description = "Product operations")
public class ProductRestController {
    private final ProductService service;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductRestController(ProductService service, RequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Get a product by id")
    public ProductDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping(path = "/full", produces = "application/json")
    @Operation(summary = "Get a page of products with full information")
    public Page<ProductDto> getPage(@ParameterObject Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping(path = "/", produces = "application/json")
    @Operation(summary = "Get a page of product with short information by filters")
    public Page<ShortProductDto> getFilteredPageOfShorts(
            @ParameterObject ProductQueryDto queryDto,
            @ParameterObject Pageable pageable) {
        return service.getPageByFilter(queryDto, pageable);
    }

    @PostMapping(path = "/", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a product")
    public ProductDto create(@RequestBody @Validated(OnCreate.class) ProductRequestDto dto) {
        return service.create(mapper.map(dto));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Update a product")
    public ProductDto update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) ProductRequestDto dto) {
        return service.update(id, mapper.map(dto));
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Partially update a product")
    public ProductDto patch(@PathVariable long id, @RequestBody @Validated(OnPatch.class) ProductRequestDto dto) {
        return service.patch(id, mapper.map(dto));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping(path = "/{id}/upload-image", consumes = "multipart/form-data", produces = "application/json")
    @Operation(summary = "Upload an image to the product")
    public ProductDto uploadImage(@PathVariable long id, @RequestParam("file") @Valid @NotNull MultipartFile file) {
        return service.uploadImage(id, file);
    }
}
