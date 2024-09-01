package com.example.bookstore.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.dto.CartAddingDto;
import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Cart;
import com.example.bookstore.model.CartId;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public CartDto getCartOfUserAndBook(UUID userId, UUID bookId) {
        User user = User.builder().id(userId).build();
        Book book = Book.builder().id(bookId).build();
        Cart cart = cartRepository.findByUserAndBook(user, book).orElse(null);
        CartDto cartDto = null;
        // user's cart has this book
        if (cart != null) {
            UserDto userDto = UserDto.builder()
                    .id(cart.getUser().getId())
                    .username(cart.getUser().getUsername())
                    .name(cart.getUser().getName())
                    .email(cart.getUser().getEmail())
                    .address(cart.getUser().getAddress())
                    .avatar(cart.getUser().getAvatar())
                    .accountNumber(cart.getUser().getAccountNumber())
                    .phoneNumber(cart.getUser().getPhoneNumber())
                    .role(cart.getUser().getRole().getName())
                    .status(cart.getUser().getStatus())
                    .build();
            cartDto = CartDto.builder()
                    .user(userDto)
                    .book(cart.getBook())
                    .quantity(cart.getQuantity())
                    .build();
        }
        return cartDto;
    }

    @Override
    public boolean addToCart(CartAddingDto cartDto) {
        Book book = bookRepository.findById(cartDto.getBookId())
                .orElseThrow(() -> new CustomException(404, "Book not found"));

        // check stock quantity
        if (book.getStockQuantity() < cartDto.getQuantity()) {
            return false;
        }

        User user = User.builder().id(cartDto.getUserId()).build();
        Cart cart = cartRepository.findByUserAndBook(user, book)
                .orElse(null);
        // if user has already put this book to cart
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + cartDto.getQuantity());
        } else {
            cart = Cart.builder()
                    .id(new CartId(user.getId(), book.getId()))
                    .quantity(cartDto.getQuantity())
                    .user(user)
                    .book(book)
                    .build();
        }
        // insert if not exist and update if exist
        cartRepository.save(cart);
        return true;
    }

}
