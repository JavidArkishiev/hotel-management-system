package com.example.apigateway.exception;

public class UnauthorizedException extends Throwable {
    public UnauthorizedException(String s) {
        super(s);
    }
}
