package com.example.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.dto.UserRegisterDto;

public interface UserService {
    void addUser(UserRegisterDto userRegisterDto);

    UserDto getUserInfoByUsername(String username);

    void updateUserInfo(UserDto userDto, MultipartFile avatar);
}
