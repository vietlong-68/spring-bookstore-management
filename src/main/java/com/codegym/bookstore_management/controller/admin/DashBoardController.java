package com.codegym.bookstore_management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.service.ProductService;

@Controller
@RequestMapping("/admin")
public class DashBoardController {

    private final UserService userService;
    private final ProductService productService;

    public DashBoardController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("products", productService.findAll());
        return "admin/dashboard";
    }
}
