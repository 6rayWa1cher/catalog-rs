package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductPageController {
    private final ProductService service;
    private final ProductCategoryService criteriaService;

    @Autowired
    public ProductPageController(ProductService service, ProductCategoryService criteriaService) {
        this.service = service;
        this.criteriaService = criteriaService;
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable long id) {
        ProductDto productDto = service.getById(id);
        model.addAttribute("product", productDto);
        if (productDto.getCategory() != null) {
            ProductCategoryDto categoryDto = criteriaService.getById(productDto.getCategory());
            model.addAttribute("category", categoryDto);
        }
        return "product-page";
    }
}
