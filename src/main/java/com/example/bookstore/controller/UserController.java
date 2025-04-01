package com.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public String getUserInfo(HttpSession session, Model model) {
        model.addAttribute("updateStatus", session.getAttribute("updateStatus"));
        session.removeAttribute("updateStatus");
        return "profile";
    }

    @PostMapping("")
    public String updateUserInfo(UserDto userDto, @RequestParam MultipartFile avatarImage, Model model,
            HttpSession session) {
        UserDto loginUser = (UserDto) model.getAttribute("loginUser");
        userDto.setId(loginUser.getId());
        try {
            userService.updateUserInfo(userDto, avatarImage);
            session.setAttribute("updateStatus", true);
        } catch (Exception e) {
            session.setAttribute("updateStatus", false);
        }
        return "redirect:/user";
    }
}
