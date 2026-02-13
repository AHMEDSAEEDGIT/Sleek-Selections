package com.ecommerce.sleekselects.service.cart;

import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Cart;
import com.ecommerce.sleekselects.repository.CartItemRepository;
import com.ecommerce.sleekselects.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final com.ecommerce.sleekselects.repository.UserRepository userRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            // Create new cart for user
            com.ecommerce.sleekselects.model.User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.delete(cart);
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        return cartRepository.save(newCart).getId();
    }
}
