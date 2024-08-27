package com.example.bookstore.service;

import com.example.bookstore.dto.PaginationDto;
import com.example.bookstore.model.Book;

public interface BookService {
    PaginationDto<Book> findBooks(String keyword, String genre, int page);
}
