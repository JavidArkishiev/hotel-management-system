package com.example.paymentservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BalanceRequestDto {

    @PositiveOrZero(message = "amount can not be negative value")
    @NotNull(message = "amount can not be Null")
    private BigDecimal amount;

}
