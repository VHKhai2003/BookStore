package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.utils.ValidationUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/place-order")
    public String placeOrder(Model model, RecipientDto recipientDto, HttpSession session) {
        boolean valid = true;
        if (!ValidationUtils.notBlankValidate(recipientDto.getName())) {
            session.setAttribute("nameError", "(*) Name is required!");
            valid = false;
        }
        if (!ValidationUtils.validatePhone(recipientDto.getPhoneNumber())) {
            session.setAttribute("phoneError", "(*) Invalid phone number!");
            valid = false;
        }
        if (!ValidationUtils.notBlankValidate(recipientDto.getAddress())) {
            session.setAttribute("addressError", "(*) Address is required!");
            valid = false;
        }
        if (valid) {
            // implement later ...
            return "redirect:/order";
        }
        return "redirect:/cart/summary";
    }

    @GetMapping("")
    public String getOrdersList(Model model) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        List<Order> orders = orderRepository.findByOrderBy(User.builder().id(user.getId()).build());
        model.addAttribute("currentPage", "order");
        model.addAttribute("orders", orders);
        return "order";
    }
}
