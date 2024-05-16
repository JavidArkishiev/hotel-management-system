package com.example.pricingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "additional_service-price", nullable = false)
    private BigDecimal additionalServicePrice;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

}
