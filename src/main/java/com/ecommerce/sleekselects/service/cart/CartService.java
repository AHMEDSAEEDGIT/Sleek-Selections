package com.ecommerce.sleekselects.service.cart;

import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Cart;
import com.ecommerce.sleekselects.repository.CartItemRepository;
import com.ecommerce.sleekselects.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found "));

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart =getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart =getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(){
        Cart newCart = new Cart();
        Long cartId = cartIdGenerator.incrementAndGet();
        newCart.setId(cartId);
        return cartRepository.save(newCart).getId();
    }
}
