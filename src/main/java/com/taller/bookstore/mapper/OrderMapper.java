package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.Order;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setUserEmail(order.getUser().getEmail());
        response.setStatus(order.getStatus().name());
        response.setTotal(order.getTotal());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(order.getItems().stream().map(orderItemMapper::toResponse).collect(Collectors.toList()));
        return response;
    }
}
