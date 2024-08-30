package com.example.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.dto.UserRegisterDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(UserRegisterDto userRegisterDto) {
        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new CustomException(400, "Username existed");
        }

        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .name(userRegisterDto.getFullname())
                .email(userRegisterDto.getEmail())
                .avatar("/images/users/avatars/default.jpg")
                .role(Role.builder().id(1).name("User").build())
                .status("active")
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDto getUserInfoByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(404, username + " not found"));
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(username)
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .accountNumber(user.getAccountNumber())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getName())
                .status(user.getStatus())
                .build();
        return userDto;
    }

}
