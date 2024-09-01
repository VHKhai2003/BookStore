package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Cart;
import com.example.bookstore.model.CartId;
import com.example.bookstore.model.User;
import com.example.bookstore.model.Book;

public interface CartRepository extends JpaRepository<Cart, CartId> {
    Optional<Cart> findByUserAndBook(User user, Book book);

    List<Cart> findByUser(User user);
}
