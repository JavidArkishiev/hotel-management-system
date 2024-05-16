package com.example.customerservice.exception;

public class UserNotfoundException extends RuntimeException {
    public UserNotfoundException(String s) {
        super(s);
    }
}
