package com.example.reservationservice.exception;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String s) {
        super(s);
    }
}
