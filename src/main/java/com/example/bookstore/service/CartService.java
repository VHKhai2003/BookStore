package com.example.bookstore.service;

import java.util.UUID;

import com.example.bookstore.dto.CartDto;

public interface CartService {
    CartDto getCartOfUserAndBook(UUID userId, UUID bookId);
}
