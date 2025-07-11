package com.codegym.bookstore_management.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.codegym.bookstore_management.model.Cart;
import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.service.ProductService;
import com.codegym.bookstore_management.service.SomethingConverter;
import com.codegym.bookstore_management.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.codegym.bookstore_management.model.CartDetail;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

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
        return "redirect:/cart/show";
    }

    @GetMapping("/show")
    public String showCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User user = somethingConverter.userStoUser(userS);
        Cart cart = cartService.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartDetails(new ArrayList<>());
        }
        List<CartDetail> currentCartDetails = cart.getCartDetails();
        if (currentCartDetails == null) {
            currentCartDetails = new ArrayList<>();
            cart.setCartDetails(currentCartDetails);
        }
        double calculatedTotalAmount = 0.0;
        List<CartDetail> cartDetailListForView = new ArrayList<>();
        for (CartDetail detail : currentCartDetails) {
            Product product = productService.findById(detail.getProductId());
            if (product != null) {
                calculatedTotalAmount += (double) detail.getQuantity() * product.getPrice();
            }
            cartDetailListForView.add(detail);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailListForView);
        model.addAttribute("totalAmount", calculatedTotalAmount);
        Map<Long, Product> productMap = new java.util.HashMap<>();
        for (CartDetail detail : currentCartDetails) {
            Product product = productService.findById(detail.getProductId());
            if (product != null) {
                productMap.put(detail.getProductId(), product);
            }
        }
        model.addAttribute("productMap", productMap);
        return "client/cart/show";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("cartDetailId") Long cartDetailId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        User user = somethingConverter.userStoUser(userS);
        
        cartService.removeFromCart(cartDetailId, user);
        return "redirect:/cart/show";
    }
}
