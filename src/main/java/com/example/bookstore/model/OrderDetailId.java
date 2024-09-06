package com.example.bookstore.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class OrderDetailId {
    private UUID orderId;
    private UUID bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, bookId);
    }
}
