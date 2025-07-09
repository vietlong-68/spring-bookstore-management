package com.codegym.bookstore_management.controller.client;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.service.ProductService;
import com.codegym.bookstore_management.service.SomethingConverter;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final SomethingConverter somethingConverter;

    public HomePageController(ProductService productService, SomethingConverter mapperService) {
        this.productService = productService;
        this.somethingConverter = mapperService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        return "client/homepage/show";
    }
}