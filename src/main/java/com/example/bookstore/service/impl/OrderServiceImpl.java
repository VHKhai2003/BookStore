package com.example.bookstore.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Cart;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.model.OrderDetailId;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.OrderDetailRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

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
    @Transactional
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
            Book book = item.getBook();
            book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
            bookRepository.save(book);
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

    @Override
    public List<Order> getPayedOrders() {
        return orderRepository.findByDeliveryStatusNotIn("Pending", "Cancel");
    }

    @Override
    public Float getEarningCurrentYear(List<Order> orders) {
        float earning = 0;
        int currentYear = (new Date()).getYear();
        for (Order order : orders) {
            if (order.getOrderDate().getYear() == currentYear) {
                earning = earning + order.getTotalAmount();
            }
        }
        return (float) Math.round(earning * 100) / 100;
    }

    @Override
    public Float getEarningCurrentMonth(List<Order> orders) {
        float earning = 0;
        Date today = new Date();
        int currentYear = today.getYear();
        int currentMonth = today.getMonth();
        for (Order order : orders) {
            if (order.getOrderDate().getYear() == currentYear && order.getOrderDate().getMonth() == currentMonth) {
                earning = earning + order.getTotalAmount();
            }
        }
        return (float) Math.round(earning * 100) / 100;
    }

    @Override
    public List<Float> getEarningEachYear(List<Order> orders) {
        List<Float> earnings = new ArrayList<>();
        int currentYear = (new Date()).getYear();
        for (int i = currentYear - 4; i <= currentYear; i++) {
            float sum = 0;
            for (Order order : orders) {
                if (order.getOrderDate().getYear() == i) {
                    sum = sum + order.getTotalAmount();
                }
            }
            earnings.add(sum);
        }
        return earnings;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void updateDeliveryStatus(UUID id, String newStatus) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new CustomException(404, "Order not found"));
        if(newStatus.equals("Processing") 
            || newStatus.equals("Shipping") 
            || newStatus.equals("Shipped") 
            || newStatus.equals("Cancel")) {
                order.setDeliveryStatus(newStatus);
                orderRepository.save(order);
                return;
        }
        throw new CustomException(400, "Invalid delivery status");
    }

}
