package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.OrderItemRequest;
import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.Book;
import com.taller.bookstore.entity.Order;
import com.taller.bookstore.entity.OrderItem;
import com.taller.bookstore.entity.OrderStatus;
import com.taller.bookstore.entity.Role;
import com.taller.bookstore.entity.User;
import com.taller.bookstore.exception.custom.InsufficientStockException;
import com.taller.bookstore.exception.custom.InvalidOrderStateException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.exception.custom.UnauthorizedAccessException;
import com.taller.bookstore.mapper.OrderMapper;
import com.taller.bookstore.repository.BookRepository;
import com.taller.bookstore.repository.OrderRepository;
import com.taller.bookstore.repository.UserRepository;
import com.taller.bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            BookRepository bookRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse create(String userEmail, OrderRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email: " + userEmail));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("book with id " + itemRequest.getBookId() + " not found"));

            if (book.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("insufficient stock for book with id " + book.getId());
            }

            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setQuantity(itemRequest.getQuantity());
            item.setSubtotal(book.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            order.addItem(item);
            total = total.add(item.getSubtotal());
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findMyOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email: " + userEmail));
        return orderRepository.findByUserId(user.getId()).stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse confirm(Long orderId) {
        Order order = getOrder(orderId);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("order with id " + orderId + " cannot be confirmed in state " + order.getStatus());
        }

        for (OrderItem item : order.getItems()) {
            Book book = item.getBook();
            if (book.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("insufficient stock for book with id " + book.getId());
            }
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse cancel(Long orderId, String userEmail, boolean isAdmin) {
        Order order = getOrder(orderId);
        if (!isAdmin && !order.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("user cannot access order with id " + orderId);
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException("order with id " + orderId + " cannot be cancelled because it is CONFIRMED");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order with id " + orderId + " not found"));
    }
}
