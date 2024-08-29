package com.example.bookstore.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String avatar;
    private String accountNumber;
    private String phoneNumber;
    private String role;
    private String status;
}
