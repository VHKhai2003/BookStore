package com.example.bookstore.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.OrderDetailRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<Order> getOrdersByUser(UUID userID) {
        return orderRepository.findByOrderBy(User.builder().id(userID).build());
    }

    @Override
    public List<OrderDetail> getDetailsOfOrder(UUID orderId) {
        return orderDetailRepository.findByOrder(Order.builder().id(orderId).build());
    }

}
