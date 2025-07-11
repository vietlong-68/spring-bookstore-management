package com.codegym.bookstore_management.service;

import java.util.List;
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

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void HandleAddProductToCart(Long productId, User user, Integer quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        CartDetail existingDetail = cartDetailRepository.findByCartAndProductId(cart.getId(), productId);
        if (existingDetail != null) {
            existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            Double latestPrice = productService.findById(productId).getPrice();
            existingDetail.setPrice(latestPrice);
            cartDetailRepository.save(existingDetail);
        } else {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProductId(productId);
            cartDetail.setQuantity(quantity);
            Double price = productService.findById(productId).getPrice();
            cartDetail.setPrice(price);
            cartDetailRepository.save(cartDetail);
        }
    }

    public void removeFromCart(Long cartDetailId, User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            CartDetail toRemove = null;
            for (CartDetail detail : cartDetails) {
                if (detail.getId().equals(cartDetailId)) {
                    toRemove = detail;
                    break;
                }
            }
            if (toRemove != null) {
                cartDetails.remove(toRemove);
                cartRepository.save(cart);
            }
        }
    }
}
