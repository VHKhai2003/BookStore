package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.service.UserService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}/block")
    public Boolean blockUser(@PathVariable UUID id) {
        userService.blockUser(id);
        return true;    
    }

    @GetMapping("/users/{id}/unblock")
    public Boolean unblockUser(@PathVariable UUID id) {
        userService.unblockUser(id);
        return true;    
    }
}
