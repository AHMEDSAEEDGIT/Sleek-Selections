package com.ecommerce.sleekselects.repository;

import com.ecommerce.sleekselects.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}