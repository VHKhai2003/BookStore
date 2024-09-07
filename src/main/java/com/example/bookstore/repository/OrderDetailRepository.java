package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.model.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrder(Order order);
}
