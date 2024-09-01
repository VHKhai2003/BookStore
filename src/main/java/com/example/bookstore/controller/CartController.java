package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String getCartInfo(Model model) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        List<CartDto> cartDtos = cartService.getCartOfUser(user.getId());
        model.addAttribute("cartInfo", cartDtos);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(CartAddingDto cartDto, HttpSession session) {
        session.setAttribute("addStatus", cartService.addToCart(cartDto));
        return "redirect:/book/" + cartDto.getBookId().toString();
    }
}
