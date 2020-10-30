package com.domgarr.concetto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataIntegrityError extends RuntimeException {

    public DataIntegrityError(String message) {
        super(message);
    }


}
