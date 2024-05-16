package com.example.reservationservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationUpdateRequestDto {
    @NotNull(message = "roomId can not be null")
    @PositiveOrZero(message = "roomId must be a non-negative value")
    Long roomId;
    @NotNull(message = "checkInDate may not be null")
    LocalDate checkInDate;
    @NotNull(message = "checkoutDate may not be null")
    LocalDate checkoutDate;
}
