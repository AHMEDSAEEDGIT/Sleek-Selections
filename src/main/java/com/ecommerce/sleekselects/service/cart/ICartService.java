package com.ecommerce.sleekselects.service.cart;

import com.ecommerce.sleekselects.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
