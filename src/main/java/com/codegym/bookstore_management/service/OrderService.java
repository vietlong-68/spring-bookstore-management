package com.codegym.bookstore_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import org.springframework.stereotype.Service;

import com.codegym.bookstore_management.model.Cart;
import com.codegym.bookstore_management.model.CartDetail;
import com.codegym.bookstore_management.model.Order;
import com.codegym.bookstore_management.model.OrderDetail;
import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.repository.OrderDetailRepository;
import com.codegym.bookstore_management.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void handleOrder(Cart cart, String receiverAddress, String receiverPhone, String receiverName) {
        Order order = new Order();
        order.setReceiverAddress(receiverAddress);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverName(receiverName);
        order.setTotalPrice(cartService.calculateTotalAmount(cart));
        order.setUser(cart.getUser());
        order.setStatus("PENDING");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cart.getCartDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            Product product = productService.findById(cartDetail.getProductId());
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        cartService.deleteCart(cart);
    }

}
