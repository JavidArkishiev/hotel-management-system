package com.example.paymentservice.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private String message;
    private LocalDateTime dateTime;
    private Integer statusCode;
}
