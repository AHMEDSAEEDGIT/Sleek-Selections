package com.ecommerce.sleekselects.controller;


import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.response.ApiResponse;
import com.ecommerce.sleekselects.service.cart.ICartItemService;
import com.ecommerce.sleekselects.service.cart.ICartService;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId ,
                                                     @RequestParam Long productId ,
                                                     @RequestParam Integer quantity){
        try {

            if(cartId == null){
                cartId = cartService.initializeNewCart();
            }

            cartItemService.addItemToCart(cartId,productId,quantity);
            return  ResponseEntity.ok(new ApiResponse("Add item success" ,null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId ,@PathVariable Long itemId){

        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Item removed successfuly" ,null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }

    }

    @PutMapping("/cart/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId ,
                                                          @PathVariable Long itemId ,
                                                          @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId,itemId , quantity);
            return ResponseEntity.ok(new ApiResponse("Item updated successfuly" ,null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }

    }
}
