package com.example.reservationservice.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "room_id", nullable = false)
    Long roomId;

    @Column(name = "customer_id", nullable = false)
    Long customerId;

    @Column(name = "check_in_date", nullable = false)
    LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    LocalDate checkoutDate;

    @Column(name = "total_price", nullable = false)
    BigDecimal totalPrice;

    @Column(name = "payment_status", nullable = false)
    boolean paymentStatus;

    @Column(name = "is_active", nullable = false)
    boolean isActive;
    LocalDateTime creationDate = LocalDateTime.now();


}
