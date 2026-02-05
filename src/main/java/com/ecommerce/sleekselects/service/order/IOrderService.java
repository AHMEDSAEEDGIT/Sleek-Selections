package com.ecommerce.sleekselects.service.order;

import com.ecommerce.sleekselects.dto.OrderDto;
import com.ecommerce.sleekselects.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
