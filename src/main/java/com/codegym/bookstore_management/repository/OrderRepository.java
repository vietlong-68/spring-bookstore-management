package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegym.bookstore_management.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
