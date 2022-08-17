package com.a6raywa1cher.test.catalogrs.rest;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.mapper.RestDtoMapper;
import com.a6raywa1cher.test.catalogrs.rest.dto.ProductCategoryRestDto;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnPatch;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnUpdate;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
    private final ProductCategoryService service;
    private final RestDtoMapper mapper;

    @Autowired
    public ProductCategoryController(ProductCategoryService service, RestDtoMapper mapper) {
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
    public ProductCategoryDto create(@RequestBody @Validated(OnCreate.class) ProductCategoryRestDto dto) {
        return service.create(mapper.map(dto));
    }

    @PutMapping("/{id}")
    public ProductCategoryDto update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) ProductCategoryRestDto dto) {
        return service.update(id, mapper.map(dto));
    }

    @PatchMapping("/{id}")
    public ProductCategoryDto patch(@PathVariable long id, @RequestBody @Validated(OnPatch.class) ProductCategoryRestDto dto) {
        return service.patch(id, mapper.map(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
