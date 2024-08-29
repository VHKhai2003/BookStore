package com.example.bookstore.service;

import java.util.UUID;

import com.example.bookstore.dto.PaginationDto;
import com.example.bookstore.model.Book;

public interface BookService {
    PaginationDto<Book> findBooks(String keyword, String genre, int page);

    Book getBookInfo(UUID id);
}
