package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegym.bookstore_management.model.Cart;
import com.codegym.bookstore_management.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
