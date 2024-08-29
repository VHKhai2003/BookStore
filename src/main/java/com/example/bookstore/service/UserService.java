package com.example.bookstore.service;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.dto.UserRegisterDto;

public interface UserService {
    void addUser(UserRegisterDto userRegisterDto);

    UserDto getUserInfoByUsername(String username);
}
