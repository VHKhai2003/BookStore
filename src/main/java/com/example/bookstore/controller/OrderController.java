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

import com.example.bookstore.dto.RecipientDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderDetail;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.utils.ValidationUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

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
            // call service to create an order
            UserDto user = (UserDto) model.getAttribute("loginUser");
            UUID orderId = orderService.placeOrder(recipientDto, user);
            session.setAttribute("placeOrderMessage", "Place order successfully");
            return "redirect:/order/detail/" + orderId.toString();
        }
        return "redirect:/cart/summary";
    }

    @GetMapping("")
    public String getOrdersList(Model model) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        List<Order> orders = orderService.getOrdersByUser(user.getId());
        model.addAttribute("currentPage", "order");
        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/detail/{id}")
    public String getOrderDetail(@PathVariable UUID id, Model model, HttpSession session) {
        List<OrderDetail> orderDetails = orderService.getDetailsOfOrder(id);
        model.addAttribute("currentPage", "order");
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", orderDetails.get(0).getOrder());
        model.addAttribute("updateStatus", session.getAttribute("updateStatus"));
        model.addAttribute("nameError", session.getAttribute("nameError"));
        model.addAttribute("phoneError", session.getAttribute("phoneError"));
        model.addAttribute("addressError", session.getAttribute("addressError"));
        model.addAttribute("placeOrderMessage", session.getAttribute("placeOrderMessage"));
        session.removeAttribute("updateStatus");
        session.removeAttribute("nameError");
        session.removeAttribute("phoneError");
        session.removeAttribute("addressError");
        session.removeAttribute("placeOrderMessage");
        return "order-detail";
    }

    @PostMapping("/detail/{id}")
    public String updateOrder(@PathVariable UUID id, RecipientDto recipientDto, Model model, HttpSession session) {
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
            session.setAttribute("updateStatus", orderService.updateRecipentInfo(id, recipientDto));
        }
        return "redirect:/order/detail/" + id.toString();
    }
}
