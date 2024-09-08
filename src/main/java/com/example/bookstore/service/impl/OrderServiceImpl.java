package com.example.bookstore.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.exception.CustomException;
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

    @Override
    public boolean updateRecipentInfo(UUID orderId, RecipientDto recipient) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(400, "Bad request"));

        // cannot update when the order is shipping, shipped or cancel
        String orderStatus = order.getDeliveryStatus();
        if (orderStatus.equalsIgnoreCase("shipping") || orderStatus.equalsIgnoreCase("shipped")
                || orderStatus.equalsIgnoreCase("Cancel")) {
            return false;
        }

        order.setReceiver(recipient.getName());
        order.setPhoneNumber(recipient.getPhoneNumber());
        order.setDeliveryAddress(recipient.getAddress());
        orderRepository.save(order);
        return true;
    }

}
