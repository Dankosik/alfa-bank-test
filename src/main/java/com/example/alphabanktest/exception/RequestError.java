package com.example.alphabanktest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class RequestError {
    private final HttpStatus error;
    private final String message;
    private Date timestamp;
    private int status;
}
