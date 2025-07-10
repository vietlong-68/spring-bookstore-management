package com.codegym.bookstore_management.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.service.ProductService;
import com.codegym.bookstore_management.service.SomethingConverter;
import com.codegym.bookstore_management.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/cart")
@Controller
public class CartController {

    private final SomethingConverter somethingConverter;
    private final CartService cartService;
    private final ProductService productService;

    public CartController(SomethingConverter somethingConverter, CartService cartService,
            ProductService productService) {
        this.somethingConverter = somethingConverter;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        User user = somethingConverter.userStoUser(userS);
        cartService.HandleAddProductToCart(productId, user, quantity);
        return "redirect:/";
    }
}
