package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.model.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

}
