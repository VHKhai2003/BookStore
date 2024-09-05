package com.example.bookstore.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${static-path.images.avatars.default}")
    private String defaultImage;
    @Value("${static-path.images.avatars.prefix}")
    private String avatarsPrefix;
    @Value("${static-path.url}")
    private String staticPath;

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

    @Override
    public void updateUserInfo(UserDto userDto, MultipartFile avatar) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new CustomException(404, "User not found"));
        if (!avatar.isEmpty()) {
            // update avatar
            String currentAvatarPath = user.getAvatar();
            String newAvatarName = avatar.getOriginalFilename();
            try {
                if (!currentAvatarPath.equals(defaultImage)) {
                    // current avatar is not default avatar
                    String currentExt = currentAvatarPath.substring(currentAvatarPath.lastIndexOf("."));
                    String updatedExt = newAvatarName.substring(newAvatarName.lastIndexOf("."));
                    if (!currentExt.equals(updatedExt)) {
                        Files.deleteIfExists(Paths.get(staticPath, currentAvatarPath).toAbsolutePath());
                        user.setAvatar(avatarsPrefix + newAvatarName);
                    }
                } else {
                    user.setAvatar(avatarsPrefix + newAvatarName);
                }
                Path path = Paths.get(staticPath, user.getAvatar()).toAbsolutePath();
                Files.write(path, avatar.getBytes());
            } catch (IOException e) {
                throw new CustomException(500, "Cannot update image");
            }
        }
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
    }

}
