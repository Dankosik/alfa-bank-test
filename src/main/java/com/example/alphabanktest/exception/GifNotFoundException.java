package com.example.alphabanktest.exception;

public class GifNotFoundException extends RuntimeException {
    public GifNotFoundException(Throwable cause) {
        super(cause);
    }

    public GifNotFoundException(String message) {
        super(message);
    }
}
