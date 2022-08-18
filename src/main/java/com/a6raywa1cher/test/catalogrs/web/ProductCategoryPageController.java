package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductCategoryRequestDto;
import com.a6raywa1cher.test.catalogrs.mapper.RequestDtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import com.a6raywa1cher.test.catalogrs.validation.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.validation.group.OnUpdate;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/products/categories")
public class ProductCategoryPageController {
    private final ProductCategoryService service;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductCategoryPageController(ProductCategoryService service, RequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public String getCategories(Model model, @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductCategoryDto> categoryDtoList = service.getPage(pageable);
        model.addAttribute("categories", categoryDtoList);
        return "categories-page";
    }

    @ModelAttribute("category")
    public ProductCategoryRequestDto createProductCategoryDto() {
        return new ProductCategoryRequestDto();
    }

    @GetMapping("/create")
    public String createCategoryForm(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Создание категории");
        model.addAttribute("actionUrl", request.getRequestURL().toString() + ".do");
        return "category-form";
    }

    @PostMapping("/create.do")
    public String createCategoryAction(
            @ModelAttribute("category") @Validated(OnCreate.class) ProductCategoryRequestDto dto,
            BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Создание категории");
            model.addAttribute("actionUrl", request.getRequestURL().toString());
            return "category-form";
        }
        service.create(mapper.map(dto));
        return "redirect:/products/categories/";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryPage(Model model, @PathVariable long id, HttpServletRequest request) {
        ProductCategoryDto dto = service.getById(id);
        model.addAttribute("title", "Изменение категории");
        model.addAttribute("category", dto);
        model.addAttribute("categoryId", id);
        model.addAttribute("actionUrl", request.getRequestURL().toString() + ".do");
        return "category-form";
    }

    @PostMapping("/{id}/edit.do")
    public String editCategoryAction(
            @ModelAttribute("category") @Validated(OnUpdate.class) ProductCategoryRequestDto dto,
            @PathVariable long id, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Изменение категории");
            model.addAttribute("categoryId", id);
            model.addAttribute("actionUrl", request.getRequestURL().toString());
            return "category-form";
        }
        service.update(id, mapper.map(dto));
        return "redirect:/products/categories/";
    }

    @PostMapping("/{id}/delete.do")
    public String deleteCategoryAction(@PathVariable long id) {
        service.delete(id);
        return "redirect:/products/categories/";
    }
}
