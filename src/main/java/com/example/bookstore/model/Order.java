package com.example.bookstore.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity(name = "Order")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_by")
    private UUID orderBy;

    @Column(name = "order_date")
    @CreationTimestamp
    private Date orderDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_date")
    @Temporal(value = TemporalType.DATE)
    private Date deliveryDate;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String receiver;
}
