package com.example.alphabanktest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(value = {CurrencyNotFoundException.class})
    protected ResponseEntity<RequestError> handleCurrencyNotFoundException(CurrencyNotFoundException exception) {
        RequestError requestError =
                new RequestError(HttpStatus.NOT_FOUND, exception.getMessage(), new Date(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(requestError, requestError.getError());
    }

    @ExceptionHandler(value = {GifNotFoundException.class})
    protected ResponseEntity<RequestError> handleGifNotFoundException(GifNotFoundException exception) {
        RequestError requestError =
                new RequestError(HttpStatus.NOT_FOUND, exception.getMessage(), new Date(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(requestError, requestError.getError());
    }
}
