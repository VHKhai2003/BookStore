package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.model.Order;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;

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
}
