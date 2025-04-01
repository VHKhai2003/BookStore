package com.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.dto.UserRegisterDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model) {
        model.addAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(HttpSession session, Model model) {
        model.addAttribute("message", session.getAttribute("message"));
        model.addAttribute("registerInfo", session.getAttribute("registerInfo"));
        session.removeAttribute("message");
        session.removeAttribute("registerInfo");
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(UserRegisterDto userRegisterDto, HttpSession session) {
        try {
            userService.addUser(userRegisterDto);
            session.setAttribute("message", "Register successfully, login with your new account");
        } catch (CustomException e) {
            session.setAttribute("message", e.getMessage());
            session.setAttribute("registerInfo", userRegisterDto);
            return "redirect:/auth/register";
        }
        return "redirect:/auth/login";
    }
}
