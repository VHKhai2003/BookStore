package com.example.bookstore.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.dto.UserRegisterDto;
import com.example.bookstore.model.User;

public interface UserService {
    void addUser(UserRegisterDto userRegisterDto);

    UserDto getUserInfoByUsername(String username);

    void updateUserInfo(UserDto userDto, MultipartFile avatar);

    List<User> getAllUsers();

    void blockUser(UUID id);
    void unblockUser(UUID id);

    User getUserById(UUID id);
}
