package com.example.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandle {


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedException(InvalidTokenException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()));

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetails> handleInvalidTokenException(UnauthorizedException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()));

    }
}
