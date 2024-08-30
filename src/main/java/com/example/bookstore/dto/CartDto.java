package com.example.bookstore.dto;

import com.example.bookstore.model.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDto {
    private UserDto user;
    private Book book;
    private Integer quantity;
}
