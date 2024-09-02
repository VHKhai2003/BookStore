package com.example.bookstore.service;

import java.util.List;
import java.util.UUID;

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.dto.CartDto;

public interface CartService {
    CartDto getCartOfUserAndBook(UUID userId, UUID bookId);

    boolean addToCart(CartAddingDto cartDto);

    List<CartDto> getCartOfUser(UUID userId);

    void removeFromCart(UUID userId, UUID bookId);

    boolean updateCart(UUID userId, UUID bookId, String operation);
}
