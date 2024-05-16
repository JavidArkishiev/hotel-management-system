package com.example.roomservice.exception;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private String message;
    private Integer statusCode;
    private LocalDateTime dateTime;
}
