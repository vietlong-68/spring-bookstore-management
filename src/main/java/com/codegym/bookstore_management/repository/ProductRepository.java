package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegym.bookstore_management.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
