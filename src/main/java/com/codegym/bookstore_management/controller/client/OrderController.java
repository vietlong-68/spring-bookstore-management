package com.codegym.bookstore_management.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.codegym.bookstore_management.model.Order;
import com.codegym.bookstore_management.model.OrderDetail;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.service.SomethingConverter;
import com.codegym.bookstore_management.service.OrderService;
import com.codegym.bookstore_management.service.CartService;
import com.codegym.bookstore_management.model.Cart;
import com.codegym.bookstore_management.model.CartDetail;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final SomethingConverter somethingConverter;
    private final CartService cartService;

    public OrderController(OrderService orderService, SomethingConverter somethingConverter, CartService cartService) {
        this.orderService = orderService;
        this.somethingConverter = somethingConverter;
        this.cartService = cartService;
    }

    @GetMapping("/order/form")
    public String orderForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User user = somethingConverter.userStoUser(userS);
        Order order = new Order();
        order.setReceiverAddress(user.getAddress());
        order.setReceiverPhone(user.getPhone());
        order.setReceiverName(user.getFullName());

        Cart cart = cartService.findByUser(user);

        Double totalAmount = cartService.calculateTotalAmount(cart);
        order.setTotalPrice(totalAmount);

        model.addAttribute("order", order);
        return "client/order/form";
    }

    @PostMapping("/order/save")
    public String saveOrder(@ModelAttribute Order order, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        User user = somethingConverter.userStoUser(userS);

        Cart cart = cartService.findByUser(user);

        try {
            orderService.handleOrder(cart, order.getReceiverAddress(), order.getReceiverPhone(),
                    order.getReceiverName());
            return "client/order/payment-success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("order", order);
            return "client/order/form";
        }
    }

}
