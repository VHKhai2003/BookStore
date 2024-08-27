package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.service.BookService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("booksData", bookService.findBooks(keyword, genre, page));
        return "index";
    }
}
