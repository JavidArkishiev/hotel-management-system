package com.example.pricingservice.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceResponseDto {
    private Long roomId;

    private BigDecimal basePrice;

    private BigDecimal additionalServicePrice;

    private BigDecimal discount;
}
