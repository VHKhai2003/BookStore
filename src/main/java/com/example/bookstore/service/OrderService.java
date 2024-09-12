package com.example.bookstore.service;

import java.util.List;
import java.util.UUID;

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;

public interface OrderService {
    List<Order> getOrdersByUser(UUID userId);

    List<OrderDetail> getDetailsOfOrder(UUID orderId);

    boolean updateRecipentInfo(UUID orderId, RecipientDto recipient);

    UUID placeOrder(RecipientDto recipientDto, UserDto userDto);

    List<Order> getOrdersByDeliveryStatus(String status);

    Float getEarningCurrentYear(List<Order> orders);

    Float getEarningCurrentMonth(List<Order> orders);

    List<Float> getEarningEachYear(List<Order> orders);
}
