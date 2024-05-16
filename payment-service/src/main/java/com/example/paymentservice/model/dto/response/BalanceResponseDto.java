package com.example.paymentservice.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class BalanceResponseDto {
    private Long customerId;

    private BigDecimal balance;

    private BigDecimal amount;

    private LocalDateTime creationDate;
}
