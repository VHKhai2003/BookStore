package com.example.bookstore.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Book")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String author;
    private String publisher;
    private String title;

    @Column(name = "full_title")
    private String fullTitle;

    private String description;
    private String isbn;
    private Float price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "front_cover_image")
    private String frontCoverImage;

    @Column(name = "back_cover_image")
    private String backCoverImage;

    private String status;

    @ManyToOne
    @JoinColumn(name = "genre")
    private Genre genre;
}
