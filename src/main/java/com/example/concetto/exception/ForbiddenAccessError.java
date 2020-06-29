package com.example.concetto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccessError extends RuntimeException{
    public ForbiddenAccessError(String message) {
        super(message);
    }
}
