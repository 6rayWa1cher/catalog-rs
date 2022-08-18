package com.a6raywa1cher.test.catalogrs.web;

import com.a6raywa1cher.test.catalogrs.dto.ProductCategoryDto;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import com.a6raywa1cher.test.catalogrs.dto.ShortProductDto;
import com.a6raywa1cher.test.catalogrs.service.ProductCategoryService;
import com.a6raywa1cher.test.catalogrs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public HomePageController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("")
    public String home(Model model, ProductQueryDto queryDto, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ShortProductDto> page = productService.getPageByFilter(queryDto, pageable);
        model.addAttribute("products", page);
        Map<Long, String> categoriesMap = productCategoryService.getAll().stream()
                .collect(Collectors.toMap(ProductCategoryDto::getId, ProductCategoryDto::getTitle));
        model.addAttribute("categories", categoriesMap);
        model.addAttribute("searchForm", queryDto);
        return "home-page";
    }
}
