package com.ecommerce.sleekselects.controller;

import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Cart;
import com.ecommerce.sleekselects.response.ApiResponse;
import com.ecommerce.sleekselects.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart (@PathVariable Long cartId){
        try {
            Cart cart =cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success" , cart ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("Clear cart success" ,null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() ,null));
        }
    }

    @GetMapping("/total-price/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount (@PathVariable Long cartId){

        try {
            BigDecimal totalPrice =cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price"  ,totalPrice));

        } catch (ResourceNotFoundException e) {

            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() ,null));
        }
    }

}
