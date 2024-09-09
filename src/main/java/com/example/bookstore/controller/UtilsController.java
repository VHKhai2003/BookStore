package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class UtilsController {
    @GetMapping("/utils/darkmode")
    public Boolean handleDarkMode(@RequestParam(defaultValue = "true") Boolean isDark, HttpSession session) {
        session.setAttribute("darkmode", isDark);
        return true;
    }
}
