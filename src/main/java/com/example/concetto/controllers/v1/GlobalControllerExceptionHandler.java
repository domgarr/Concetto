package com.example.concetto.controllers.v1;

import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityError.class)
    public ResponseEntity<String> handleConceptConstraints(Set<ConstraintViolation<Concept>> constraintViolations){
        return new ResponseEntity<String>("Error Message", HttpStatus.BAD_REQUEST);
    }
}
