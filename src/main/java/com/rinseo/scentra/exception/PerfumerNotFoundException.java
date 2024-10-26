package com.rinseo.scentra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PerfumerNotFoundException extends RuntimeException{
    public PerfumerNotFoundException(String message) {
        super(message);
    }
}
