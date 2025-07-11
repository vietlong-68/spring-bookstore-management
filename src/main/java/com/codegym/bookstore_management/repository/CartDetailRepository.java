package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codegym.bookstore_management.model.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart.id = :cartId AND cd.productId = :productId")
    CartDetail findByCartAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

}
