package com.example.reviewservice.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private String message;
    private Integer statusCode;
    private LocalDateTime dateTime;
}
