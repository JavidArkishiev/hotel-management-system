package com.example.paymentservice.model.dto.request;

import com.example.paymentservice.model.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


}
