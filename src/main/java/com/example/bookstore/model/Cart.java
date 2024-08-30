package com.example.bookstore.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Entity(name = "Cart")
@Table(name = "shopping_cart_details")
public class Cart {
    @EmbeddedId
    private CartId id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId(value = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @MapsId(value = "bookId")
    private Book book;
}
