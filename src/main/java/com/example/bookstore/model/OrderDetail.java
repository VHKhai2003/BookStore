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
@Entity(name = "OrderDetail")
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    private Integer quantity;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @MapsId(value = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @MapsId(value = "bookId")
    private Book book;
}
