package com.example.customerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private LocalDateTime timeStamp;
    private Integer statusCode;

}
