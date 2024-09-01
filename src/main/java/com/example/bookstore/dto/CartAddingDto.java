package com.example.bookstore.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartAddingDto {
    private UUID userId;
    private UUID bookId;
    private Integer quantity;
}
