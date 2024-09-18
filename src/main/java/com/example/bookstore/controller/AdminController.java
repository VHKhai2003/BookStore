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

import com.example.bookstore.dto.BookDto;
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

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("currentPage", "books");
        return "admin/books-add";
    }

    @PostMapping("/books/add")
    public String handleAddBook(BookDto bookDto, HttpSession session) {
        UUID bookId = bookService.addBook(bookDto);
        session.setAttribute("addMessage", "Add new book successfully");
        return "redirect:/admin/books/detail/" + bookId.toString();
    }

    @GetMapping("/books/detail/{bookId}")
    public String viewDetail(@PathVariable UUID bookId, Model model, HttpSession session) {
        model.addAttribute("addMessage", session.getAttribute("addMessage"));
        session.removeAttribute("addMessage");
        model.addAttribute("updateMessage", session.getAttribute("updateMessage"));
        session.removeAttribute("updateMessage");
        model.addAttribute("book", bookService.getBookInfo(bookId));
        model.addAttribute("currentPage", "books");
        return "admin/books-detail";
    }

    @PostMapping("/books/update")
    public String updateBook(BookDto bookDto, HttpSession session) {
        UUID bookId = bookService.updateBook(bookDto);
        session.setAttribute("updateMessage", "Update book successfully");
        return "redirect:/admin/books/detail/" + bookId.toString();
    }

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        model.addAttribute("currentPage", "genres");
        return "admin/genres";
    }

}
