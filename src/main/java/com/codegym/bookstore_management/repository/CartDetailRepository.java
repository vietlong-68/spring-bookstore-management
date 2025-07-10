package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codegym.bookstore_management.model.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

}
