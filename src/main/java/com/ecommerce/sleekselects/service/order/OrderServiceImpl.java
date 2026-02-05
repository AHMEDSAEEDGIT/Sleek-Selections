package com.ecommerce.sleekselects.service.order;

import com.ecommerce.sleekselects.dto.OrderDto;
import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.*;
import com.ecommerce.sleekselects.repository.CartRepository;
import com.ecommerce.sleekselects.repository.OrderRepository;
import com.ecommerce.sleekselects.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ICartService cartService;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty or not found for user id: " + userId);
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalAmount());

        // Convert CartItems to OrderItems
        HashSet<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(),
                    cartItem.getUnitPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
