package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse create(String userEmail, OrderRequest request);
    List<OrderResponse> findAll();
    List<OrderResponse> findMyOrders(String userEmail);
    OrderResponse confirm(Long orderId);
    OrderResponse cancel(Long orderId, String userEmail, boolean isAdmin);
}
