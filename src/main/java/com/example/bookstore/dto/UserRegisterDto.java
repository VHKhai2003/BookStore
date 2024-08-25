package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDto {
    private String username;
    private String password;
    private String confirmedPassword;
    private String fullname;
    private String email;
}
