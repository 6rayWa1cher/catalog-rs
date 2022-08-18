package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.mapper.RestDtoMapper;
import com.a6raywa1cher.test.catalogrs.rest.dto.ProductCategoryRestDto;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnUpdate;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/categories")
public class ProductCategoryPageController {
    private final ProductCategoryService productCategoryService;
    private final RestDtoMapper dtoMapper;

    @Autowired
    public ProductCategoryPageController(ProductCategoryService productCategoryService, RestDtoMapper dtoMapper) {
        this.productCategoryService = productCategoryService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("")
    public String getCategories(Model model, @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductCategoryDto> categoryDtoList = productCategoryService.getPage(pageable);
        model.addAttribute("categories", categoryDtoList);
        return "categories-page";
    }

    @ModelAttribute("category")
    public ProductCategoryRestDto createEmployeeModel() {
        return new ProductCategoryRestDto();
    }

    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("title", "Создание категории");
        return "category-form";
    }

    @PostMapping("/create.do")
    public String createCategoryAction(
            @ModelAttribute("category") @Validated(OnCreate.class) ProductCategoryRestDto dto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Создание категории");
            return "category-form";
        }
        productCategoryService.create(dtoMapper.map(dto));
        return "redirect:/products/categories/";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryPage(Model model, @PathVariable long id) {
        ProductCategoryDto dto = productCategoryService.getById(id);
        model.addAttribute("title", "Изменение категории");
        model.addAttribute("category", dto);
        model.addAttribute("categoryId", id);
        return "category-form";
    }

    @PostMapping("/{id}/edit.do")
    public String editCategoryAction(
            @ModelAttribute("category") @Validated(OnUpdate.class) ProductCategoryRestDto dto,
            @PathVariable long id, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Изменение категории");
            model.addAttribute("categoryId", id);
            return "category-form";
        }
        productCategoryService.update(id, dtoMapper.map(dto));
        return "redirect:/products/categories/";
    }

    @PostMapping("/{id}/delete.do")
    public String deleteCategoryAction(@PathVariable long id) {
        productCategoryService.delete(id);
        return "redirect:/products/categories/";
    }
}
