package com.example.bookstore.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.model.Order;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @GetMapping({ "", "/dashboard" })
    public String getDashBoard(Model model) {
        // get list of shipped orders
        List<Order> orders = orderService.getOrdersByDeliveryStatus("Shipped");
        // calculate sale figures
        model.addAttribute("earningCurrentYear", orderService.getEarningCurrentYear(orders));
        model.addAttribute("earningCurrentMonth", orderService.getEarningCurrentMonth(orders));
        model.addAttribute("earningEachYear", orderService.getEarningEachYear(orders));
        model.addAttribute("totalBooks", bookService.getNumberOfBooks());
        model.addAttribute("currentPage", "dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpSession session) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("currentPage", "books");
        model.addAttribute("deleteMessage", session.getAttribute("deleteMessage"));
        session.removeAttribute("deleteMessage");
        return "admin/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable UUID id, HttpSession session) {
        bookService.deleteBook(id);
        session.setAttribute("deleteMessage", "Delete book successfully");
        return "redirect:/admin/books";
    }
}
