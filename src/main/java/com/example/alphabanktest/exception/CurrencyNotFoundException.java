package com.example.alphabanktest.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(Throwable cause) {
        super(cause);
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
