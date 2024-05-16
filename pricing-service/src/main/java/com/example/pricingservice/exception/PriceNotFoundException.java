package com.example.pricingservice.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String s) {
        super(s);
    }
}
