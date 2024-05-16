package com.example.reservationservice.exception;

public class TotalPriceNotBeZeroException extends Throwable {
    public TotalPriceNotBeZeroException(String s) {
        super(s);
    }
}
