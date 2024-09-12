package com.example.bookstore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByOrderBy(User user);

    List<Order> findByDeliveryStatus(String deliveryStatus);
}
