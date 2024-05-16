package com.example.staffmanagementservice.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private Integer statusCode;
    private String message;
    private LocalDateTime localDateTime;
}
