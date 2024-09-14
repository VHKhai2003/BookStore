package com.example.bookstore.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.dto.BookDto;
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

    @Value("${static-path.url}")
    private String staticPath;
    @Value("${static-path.images.front-cover.prefix}")
    private String frontPrefix;
    @Value("${static-path.images.back-cover.prefix}")
    private String backPrefix;

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
            data = bookRepository.findByTitleContainingIgnoreCaseAndStatusIgnoreCase(keyword, "active", pageable);
        }
        return new PaginationDto<Book>(data.getNumber() + 1, data.getSize(), data.getTotalElements(),
                data.getTotalPages(),
                data.getNumberOfElements(), data.getContent());
    }

    @Override
    public Book getBookInfo(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(404, "Book not found"));
        // check book status
        if (!book.getStatus().equalsIgnoreCase("active")) {
            throw new CustomException(400, "Bad request");
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findByStatusIgnoreCase("active");
    }

    @Override
    public Long getNumberOfBooks() {
        return bookRepository.countByStatusIgnoreCase("Active");
    }

    @Override
    public void deleteBook(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(404, "Book not found"));
        // soft delete
        book.setStatus("delete");
        bookRepository.save(book);
    }

    @Override
    public UUID addBook(BookDto bookDto) {
        // check image
        MultipartFile frontCover = bookDto.getFrontCoverImage();
        MultipartFile backCover = bookDto.getBackCoverImage();
        if (frontCover == null || backCover == null || frontCover.isEmpty() || backCover.isEmpty()) {
            throw new CustomException(400, "Something is wrong with images");
        }

        try {
            // save image
            Path path1 = Paths
                    .get(staticPath, frontPrefix, System.currentTimeMillis() + frontCover.getOriginalFilename())
                    .toAbsolutePath();
            Path path2 = Paths.get(staticPath, backPrefix, System.currentTimeMillis() + backCover.getOriginalFilename())
                    .toAbsolutePath();
            Files.write(path1, frontCover.getBytes());
            Files.write(path2, backCover.getBytes());
            // save to database
            Book newBook = bookRepository.save(
                    Book.builder()
                            .author(bookDto.getAuthor())
                            .title(bookDto.getTitle())
                            .isbn(bookDto.getIsbn())
                            .description(bookDto.getDescription())
                            .price(bookDto.getPrice())
                            .stockQuantity(bookDto.getStockQuantity())
                            .genre(Genre.builder().id(bookDto.getGenre()).build())
                            .status("active")
                            .frontCoverImage(frontPrefix + path1.getFileName().toString())
                            .backCoverImage(backPrefix + path2.getFileName().toString())
                            .build());
            return newBook.getId();
        } catch (IOException e) {
            throw new CustomException(500, e.getMessage());
        }
    }

}
