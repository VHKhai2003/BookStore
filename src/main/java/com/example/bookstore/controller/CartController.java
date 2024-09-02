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

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String getCartInfo(Model model, HttpSession session) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        List<CartDto> cartDtos = cartService.getCartOfUser(user.getId());
        model.addAttribute("cartInfo", cartDtos);
        model.addAttribute("successMessage", session.getAttribute("successMessage"));
        model.addAttribute("failureMessage", session.getAttribute("failureMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("failureMessage");
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(CartAddingDto cartDto, HttpSession session) {
        session.setAttribute("addStatus", cartService.addToCart(cartDto));
        return "redirect:/book/" + cartDto.getBookId().toString();
    }

    @GetMapping("/delete/{bookId}")
    public String removeFromCart(Model model, @PathVariable UUID bookId, HttpSession session) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        try {
            cartService.removeFromCart(user.getId(), bookId);
            session.setAttribute("successMessage", "Delete this item successfully");
        } catch (Exception e) {
            session.setAttribute("failureMessage", "Failed to delete this item");
        }
        return "redirect:/cart";
    }

    @GetMapping("/increase/{bookId}")
    public String increseQuantity(Model model, @PathVariable UUID bookId, HttpSession session) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        if (cartService.updateCart(user.getId(), bookId, "increase"))
            session.setAttribute("successMessage", "Increase quantity successfully");
        else
            session.setAttribute("failureMessage", "Failed to increase quantity");
        return "redirect:/cart";
    }

    @GetMapping("/decrease/{bookId}")
    public String decreaseQuantity(Model model, @PathVariable UUID bookId, HttpSession session) {
        UserDto user = (UserDto) model.getAttribute("loginUser");
        if (cartService.updateCart(user.getId(), bookId, "decrease"))
            session.setAttribute("successMessage", "decrease quantity successfully");
        else
            session.setAttribute("failureMessage", "Failed to decrease quantity");
        return "redirect:/cart";
    }
}
