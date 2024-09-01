package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(CartAddingDto cartDto, HttpSession session) {
        session.setAttribute("addStatus", cartService.addToCart(cartDto));
        return "redirect:/book/" + cartDto.getBookId().toString();
    }
}
