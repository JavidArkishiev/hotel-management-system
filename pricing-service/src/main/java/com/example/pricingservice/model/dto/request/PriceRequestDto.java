package com.example.pricingservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceRequestDto {
    @NotNull(message = "basePrice can not be null")
    @PositiveOrZero(message = "basePrice must be a non-negative value")
    private BigDecimal basePrice;

    @NotNull(message = "additionalServicePrice can not be null")
    @PositiveOrZero(message = "additionalServicePrice must be a non-negative value")
    private BigDecimal additionalServicePrice;

    @NotNull(message = "discount can not be null")
    @PositiveOrZero(message = "discount must be a non-negative value")
    private BigDecimal discount;


}
