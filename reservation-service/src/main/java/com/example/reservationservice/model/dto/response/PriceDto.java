package com.example.reservationservice.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDto {
    private Long roomId;

    private BigDecimal basePrice;

    private BigDecimal additionalServicePrice;

    private BigDecimal discount;
}
