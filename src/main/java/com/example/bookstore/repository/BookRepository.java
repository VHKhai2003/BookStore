package com.example.bookstore.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;

public interface BookRepository extends JpaRepository<Book, UUID> {
	Page<Book> findByTitleContainingIgnoreCaseAndGenre(String title, Genre genre, Pageable pageable);

	Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
