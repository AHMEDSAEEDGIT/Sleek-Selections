package com.ecommerce.sleekselects.controller;

import com.ecommerce.sleekselects.dto.OrderDto;
import com.ecommerce.sleekselects.model.Order;
import com.ecommerce.sleekselects.response.ApiResponse;
import com.ecommerce.sleekselects.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/user/{userId}/place-order")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order Placed Successfully", order.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error Occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse("Error Occurred!", null));
        }
    }
}
