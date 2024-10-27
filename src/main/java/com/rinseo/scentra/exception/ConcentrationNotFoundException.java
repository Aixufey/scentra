package com.rinseo.scentra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ConcentrationNotFoundException extends RuntimeException{
    public ConcentrationNotFoundException(String message) {
        super(message);
    }
}
