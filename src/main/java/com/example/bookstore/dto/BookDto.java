package com.example.bookstore.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDto {
    private UUID id;
    private String author;
    private String title;
    private String description;
    private String isbn;
    private Float price;
    private Integer stockQuantity;
    private UUID genre;
    private MultipartFile frontCoverImage;
    private MultipartFile backCoverImage;
}
