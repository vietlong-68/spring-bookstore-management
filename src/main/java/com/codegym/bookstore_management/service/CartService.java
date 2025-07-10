package com.codegym.bookstore_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegym.bookstore_management.model.Cart;
import com.codegym.bookstore_management.model.CartDetail;
import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.repository.CartRepository;
import com.codegym.bookstore_management.repository.CartDetailRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private CartDetailRepository cartDetailRepository;
    private ProductService productService;
    private UserService userService;

    public CartService(CartRepository cartRepository, CartDetailRepository cartDetailRepository,
            UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public void HandleAddProductToCart(Long productId, User user, Integer quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(1);
            cartRepository.save(cart);
        }

        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setProductId(productId);
        cartDetail.setQuantity(quantity);
        Double price = productService.findById(productId).getPrice();
        cartDetail.setPrice(price);
        cartDetailRepository.save(cartDetail);
    }
}
