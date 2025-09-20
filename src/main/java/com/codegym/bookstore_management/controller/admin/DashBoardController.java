package com.codegym.bookstore_management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.service.ProductService;
import com.codegym.bookstore_management.service.OrderService;

@Controller
@RequestMapping("/admin")
public class DashBoardController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashBoardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAllUsers().size());
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalOrders", orderService.getTotalOrders());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("pendingOrders", orderService.getPendingOrdersCount());
        model.addAttribute("completedOrders", orderService.getCompletedOrdersCount());

        model.addAttribute("recentOrders", orderService.findAllOrders());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allUsers", userService.findAllUsers());

        return "admin/dashboard";
    }
}
