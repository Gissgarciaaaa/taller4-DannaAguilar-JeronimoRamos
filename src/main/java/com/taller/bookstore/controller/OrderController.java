package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody OrderRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Pedido creado correctamente", orderService.create(authentication.getName(), request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(200, "Pedidos consultados correctamente", orderService.findAll()));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findMyOrders(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(200, "Pedidos del usuario consultados correctamente", orderService.findMyOrders(authentication.getName())));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<OrderResponse>> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "Pedido confirmado correctamente", orderService.confirm(id)));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(@PathVariable Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(ApiResponse.success(200, "Pedido cancelado correctamente", orderService.cancel(id, authentication.getName(), isAdmin)));
    }
}
