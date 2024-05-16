package com.example.reviewservice.model.dto.responce;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationResponseDto {
    Long roomId;
    Long customerId;
    LocalDate checkInDate;
    LocalDate checkoutDate;
    BigDecimal totalPrice;
    boolean paymentStatus;
    boolean isActive;
}
