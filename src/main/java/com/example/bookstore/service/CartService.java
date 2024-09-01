package com.example.bookstore.service;

import java.util.UUID;

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.dto.CartDto;

public interface CartService {
    CartDto getCartOfUserAndBook(UUID userId, UUID bookId);

    boolean addToCart(CartAddingDto cartDto);
}
