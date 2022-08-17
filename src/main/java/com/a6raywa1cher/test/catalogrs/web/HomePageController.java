package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductDto;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final ProductService productService;

    @Autowired
    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String home(Model model) {
        Page<ProductDto> page = productService.getPage(Pageable.unpaged());
        model.addAttribute("products", page);
        return "home-page";
    }
}
