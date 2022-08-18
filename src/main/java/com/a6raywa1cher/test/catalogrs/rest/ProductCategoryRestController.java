package com.a6raywa1cher.test.catalogrs.rest;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductCategoryRequestDto;
import com.a6raywa1cher.test.catalogrs.mapper.RequestDtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
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

@RestController
@RequestMapping("/api/products/category")
public class ProductCategoryRestController {
    private final ProductCategoryService service;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductCategoryRestController(ProductCategoryService service, RequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ProductCategoryDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/")
    public Page<ProductCategoryDto> getPage(@ParameterObject Pageable pageable) {
        return service.getPage(pageable);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategoryDto create(@RequestBody @Validated(OnCreate.class) ProductCategoryRequestDto dto) {
        return service.create(mapper.map(dto));
    }

    @PutMapping("/{id}")
    public ProductCategoryDto update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) ProductCategoryRequestDto dto) {
        return service.update(id, mapper.map(dto));
    }

    @PatchMapping("/{id}")
    public ProductCategoryDto patch(@PathVariable long id, @RequestBody @Validated(OnPatch.class) ProductCategoryRequestDto dto) {
        return service.patch(id, mapper.map(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
