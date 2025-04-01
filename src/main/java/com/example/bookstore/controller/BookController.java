package com.example.bookstore.controller;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CartService cartService;

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("booksData", bookService.findBooks(keyword, genre, page));
        model.addAttribute("currentPage", "home");
        return "index";
    }

    @GetMapping("/book/{id}")
    public String getBookInfo(Model model, @PathVariable UUID id, Authentication authentication, HttpSession session) {
        Book book = bookService.getBookInfo(id);
        model.addAttribute("book", book);
        model.addAttribute("relatedBooks", book.getGenre().getBooks());

        // check in user's cart
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto loginUser = (UserDto) model.getAttribute("loginUser");
            CartDto cartDto = cartService.getCartOfUserAndBook(loginUser.getId(), id);
            if (cartDto != null) {
                model.addAttribute("maxQuantity", book.getStockQuantity() - cartDto.getQuantity());
            }
        }

        Boolean addStatus = (Boolean) session.getAttribute("addStatus");
        session.removeAttribute("addStatus");
        model.addAttribute("addStatus", addStatus);
        return "book";
    }
}
