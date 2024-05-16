package com.example.reservationservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationRequestDto {
    @NotNull(message = "checkInDate may not be null")
    LocalDate checkInDate;
    @NotNull(message = "checkoutDate may not be null")
    LocalDate checkoutDate;

}
