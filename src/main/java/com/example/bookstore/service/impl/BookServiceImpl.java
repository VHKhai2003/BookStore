package com.example.bookstore.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstore.dto.PaginationDto;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public PaginationDto<Book> findBooks(String keyword, String genre, int page) {
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<Book> data = null;
        if (genre != null && !genre.equals("all")) {
            Genre genreObj = Genre.builder().id(UUID.fromString(genre)).build();
            // find by title, genre
            // status = active
            data = bookRepository.findByTitleContainingIgnoreCaseAndGenreAndStatusIgnoreCase(keyword, genreObj,
                    "active", pageable);
        } else {
            data = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }
        return new PaginationDto<Book>(data.getNumber() + 1, data.getSize(), data.getTotalElements(),
                data.getTotalPages(),
                data.getNumberOfElements(), data.getContent());
    }

    @Override
    public Book getBookInfo(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(404, "Book not found"));
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllBooks'");
    }

    @Override
    public Long getNumberOfBooks() {
        return bookRepository.countByStatusIgnoreCase("Active");
    }

}
