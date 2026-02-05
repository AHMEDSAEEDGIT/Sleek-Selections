package com.ecommerce.sleekselects.service.cart;

import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Cart;
import com.ecommerce.sleekselects.model.CartItem;
import com.ecommerce.sleekselects.model.Product;
import com.ecommerce.sleekselects.repository.CartItemRepository;
import com.ecommerce.sleekselects.repository.CartRepository;
import com.ecommerce.sleekselects.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //get the cart
        Cart cart = cartService.getCart(cartId);
        //get the product
        Product product = productService.getProductById(productId);
        // check if the product already in the cart
        CartItem cartItem = getCartItem(cartId,productId);
        // if yes then increase the quantity with requested quantity
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = cart.getCartItems()
                .stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()-> new ResourceNotFoundException("Product not found."));

        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item ->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId , Long productId){
        Cart cart =cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
    }
}
