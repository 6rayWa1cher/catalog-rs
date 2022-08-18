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
public class ProductRestController {
    private final ProductService service;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductRestController(ProductService service, RequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/full")
    public Page<ProductDto> getPage(@ParameterObject Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping("/")
    public Page<ShortProductDto> getFilteredPageOfShorts(
            @ParameterObject ProductQueryDto queryDto,
            @ParameterObject Pageable pageable) {
        return service.getPageByFilter(queryDto, pageable);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody @Validated(OnCreate.class) ProductRequestDto dto) {
        return service.create(mapper.map(dto));
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) ProductRequestDto dto) {
        return service.update(id, mapper.map(dto));
    }

    @PatchMapping("/{id}")
    public ProductDto patch(@PathVariable long id, @RequestBody @Validated(OnPatch.class) ProductRequestDto dto) {
        return service.patch(id, mapper.map(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping(value = "/{id}/upload-image", consumes = {"multipart/form-data"})
    public ProductDto uploadImage(@PathVariable long id, @RequestParam("file") @Valid @NotNull MultipartFile file) {
        return service.uploadImage(id, file);
    }
}
