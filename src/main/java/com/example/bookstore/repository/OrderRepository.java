package com.example.bookstore.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
