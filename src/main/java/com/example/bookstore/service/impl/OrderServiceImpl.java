package com.example.bookstore.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Cart;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.model.OrderDetailId;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.OrderDetailRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

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

    @Override
    public UUID placeOrder(RecipientDto recipientDto, UserDto userDto) {
        List<Cart> carts = cartRepository.findByUser(User.builder().id(userDto.getId()).build());
        // check cart info
        if (carts.size() < 1) {
            // no book in cart
            throw new CustomException(400, "Bad request");
        }

        // calculate total amount
        float totalAmount = 0;
        for (Cart item : carts) {
            totalAmount += item.getQuantity() * item.getBook().getPrice();
        }
        totalAmount = (float) Math.round(totalAmount * 100) / 100;

        // add new order
        Order newOrder = Order.builder()
                .orderBy(User.builder().id(userDto.getId()).build())
                .receiver(recipientDto.getName())
                .deliveryAddress(recipientDto.getAddress())
                .phoneNumber(recipientDto.getPhoneNumber())
                .deliveryStatus("Pending")
                .totalAmount(totalAmount)
                .build();
        Order order = orderRepository.save(newOrder);

        // add order details
        for (Cart item : carts) {
            orderDetailRepository
                    .save(OrderDetail.builder()
                            .id(OrderDetailId.builder()
                                    .orderId(order.getId())
                                    .bookId(item.getBook().getId())
                                    .build())
                            .order(order)
                            .book(item.getBook())
                            .quantity(item.getQuantity())
                            .price((float) Math.round(item.getQuantity() * item.getBook().getPrice() * 100) / 100)
                            .build());
        }

        // delete item in cart
        cartRepository.deleteAll(carts);

        return order.getId();
    }

}
