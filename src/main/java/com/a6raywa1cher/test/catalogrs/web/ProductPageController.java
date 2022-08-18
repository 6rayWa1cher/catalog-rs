package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.dto.request.ProductWithFileRequestDto;
import com.a6raywa1cher.test.catalogrs.mapper.RequestDtoMapper;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import com.a6raywa1cher.test.catalogrs.validation.group.OnCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductPageController {
    private final ProductService service;
    private final ProductCategoryService criteriaService;
    private final ProductCategoryService productCategoryService;
    private final RequestDtoMapper mapper;

    @Autowired
    public ProductPageController(ProductService service, ProductCategoryService criteriaService,
                                 ProductCategoryService productCategoryService, RequestDtoMapper mapper) {
        this.service = service;
        this.criteriaService = criteriaService;
        this.productCategoryService = productCategoryService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public String productPage(Model model, @PathVariable long id) {
        ProductDto productDto = service.getById(id);
        model.addAttribute("product", productDto);
        if (productDto.getCategory() != null) {
            ProductCategoryDto categoryDto = criteriaService.getById(productDto.getCategory());
            model.addAttribute("category", categoryDto);
        }
        return "product-page";
    }

    @ModelAttribute("categories")
    public Map<Long, String> getCategories() {
        return productCategoryService.getAll().stream()
                .collect(Collectors.toMap(ProductCategoryDto::getId, ProductCategoryDto::getTitle));
    }

    @ModelAttribute("product")
    public ProductWithFileRequestDto createProductWithFileRequestDto() {
        return new ProductWithFileRequestDto();
    }

    @GetMapping("/create")
    public String createProductPage(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Создание продукта");
        model.addAttribute("actionUrl", request.getRequestURL() + ".do");
        return "product-form";
    }

    @PostMapping("/create.do")
    @Transactional
    public String createProductAction(@ModelAttribute("product") @Validated(OnCreate.class) ProductWithFileRequestDto dto,
                                      BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Создание продукта");
            model.addAttribute("actionUrl", request.getRequestURL());
            return "product-form";
        }
        ProductDto productDto = service.create(mapper.map(dto));
        if (dto.getImage() != null && dto.getImage().getSize() > 0) {
            service.uploadImage(productDto.getId(), dto.getImage());
        }
        return "redirect:/products/%s".formatted(productDto.getId());
    }

    @GetMapping("/{id}/edit")
    public String editProductPage(@PathVariable long id, Model model, HttpServletRequest request) {
        ProductDto productDto = service.getById(id);
        model.addAttribute("title", "Изменение продукта");
        model.addAttribute("actionUrl", request.getRequestURL() + ".do");
        model.addAttribute("product", mapper.map(productDto));
        model.addAttribute("productId", id);
        return "product-form";
    }

    @PostMapping("/{id}/edit.do")
    public String editProductAction(@PathVariable long id, @ModelAttribute("product") @Validated(OnCreate.class) ProductWithFileRequestDto dto,
                                    BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Изменение продукта");
            model.addAttribute("actionUrl", request.getRequestURL());
            model.addAttribute("product", dto);
            model.addAttribute("productId", id);
            return "product-form";
        }
        service.update(id, mapper.map(dto));
        if (dto.getImage() != null && dto.getImage().getSize() > 0) {
            service.uploadImage(id, dto.getImage());
        }
        return "redirect:/products/%s".formatted(id);
    }

    @PostMapping("/{id}/delete.do")
    public String deleteProductAction(@PathVariable long id) {
        service.delete(id);
        return "redirect:/";
    }
}
