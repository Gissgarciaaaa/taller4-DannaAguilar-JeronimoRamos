package com.taller.bookstore.repository;

import com.taller.bookstore.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findAll();

    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findByUserId(Long userId);
}
