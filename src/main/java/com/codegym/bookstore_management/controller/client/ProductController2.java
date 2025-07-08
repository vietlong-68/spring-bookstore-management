package com.codegym.bookstore_management.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.service.ProductService;

@Controller
public class ProductController2 {
    private final ProductService productService;

    public ProductController2(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

}
