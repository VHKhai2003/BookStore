package com.example.bookstore.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.model.Genre;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.GenreService;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookService bookService;
    private final OrderService orderService;
    private final GenreService genreService;
    private final UserService userService;

    @GetMapping({ "", "/dashboard" })
    public String getDashBoard(Model model) {
        // get list of payed orders
        List<Order> orders = orderService.getPayedOrders();
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
    public String getAllGenres(Model model, HttpSession session) {
        model.addAttribute("currentPage", "genres");
        model.addAttribute("deleteStatus", session.getAttribute("deleteStatus"));
        session.removeAttribute("deleteStatus");
        model.addAttribute("addStatus", session.getAttribute("addStatus"));
        session.removeAttribute("addStatus");
        model.addAttribute("updateStatus", session.getAttribute("updateStatus"));
        session.removeAttribute("updateStatus");
        return "admin/genres";
    }

    @PostMapping("/genres/add")
    public String addGenre(Genre genre, HttpSession session) {
        genreService.addGenre(genre);
        session.setAttribute("addStatus", true);
        return "redirect:/admin/genres";
    }

    @PostMapping("/genres/delete")
    public String deleteGenre(@RequestParam UUID genreId, HttpSession session) {
        session.setAttribute("deleteStatus", genreService.deleteGenre(genreId));
        return "redirect:/admin/genres";
    }

    @PostMapping("/genres/update")
    public String updateGenre(Genre genre, HttpSession session) {
        genreService.update(genre);
        session.setAttribute("updateStatus", true);
        return "redirect:/admin/genres";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("currentPage", "users");
        model.addAttribute("users", userService.getAllUsers());
        return "/admin/users";
    }
    
    @GetMapping("/users/{id}/profile")
    public String viewUserProfile(@PathVariable UUID id, Model model) {
        model.addAttribute("currentPage", "users");
        model.addAttribute("user", userService.getUserById(id));
        return "/admin/users-profile";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("currentPage", "orders");
        model.addAttribute("orders", orderService.getAllOrders());
        return "/admin/orders";
    }

    @GetMapping("/orders/{id}/detail")
    public String getOrderDetail(@PathVariable UUID id, Model model) {
        List<OrderDetail> orderDetails = orderService.getDetailsOfOrder(id);
        model.addAttribute("currentPage", "orders");
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", orderDetails.get(0).getOrder());
        return "/admin/orders-detail";
    }
     
    @PostMapping("/orders/{id}/change-status")
    public String postMethodName(@PathVariable UUID id, String deliveryStatus) {
        orderService.updateDeliveryStatus(id, deliveryStatus);
        return "redirect:/admin/orders/" + id +"/detail";
    }
    
}
