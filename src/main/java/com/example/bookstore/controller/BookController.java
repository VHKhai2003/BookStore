package com.example.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BookController {
    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/test")
    public String getHomePage1() {
        return "index";
    }

    @GetMapping("/admin/test")
    public String getHomePage3() {
        return "index";
    }
}
