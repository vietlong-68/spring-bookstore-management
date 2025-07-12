package com.codegym.bookstore_management.service;

import org.springframework.stereotype.Service;

import com.codegym.bookstore_management.model.Order;
import com.codegym.bookstore_management.repository.OrderDetailRepository;
import com.codegym.bookstore_management.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

}
