package com.example.bookstore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;

public interface BookRepository extends JpaRepository<Book, UUID> {
	Page<Book> findByTitleContainingIgnoreCaseAndGenreAndStatusIgnoreCase(String title, Genre genre, String status,
			Pageable pageable);

	Page<Book> findByTitleContainingIgnoreCaseAndStatusIgnoreCase(String title, String status, Pageable pageable);

	List<Book> findByStatusIgnoreCase(String status);

	long countByStatusIgnoreCase(String status);
}
