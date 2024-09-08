package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecipientDto {
    private String name;
    private String phoneNumber;
    private String address;
}
