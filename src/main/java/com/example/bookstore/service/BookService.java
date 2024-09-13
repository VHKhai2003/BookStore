package com.example.bookstore.service;

import java.util.List;
import java.util.UUID;

import com.example.bookstore.dto.PaginationDto;
import com.example.bookstore.model.Book;

public interface BookService {
    PaginationDto<Book> findBooks(String keyword, String genre, int page);

    Book getBookInfo(UUID id);

    List<Book> getAllBooks();

    Long getNumberOfBooks();

    void deleteBook(UUID id);
}
