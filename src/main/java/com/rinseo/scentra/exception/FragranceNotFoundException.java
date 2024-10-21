package com.rinseo.scentra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FragranceNotFoundException extends RuntimeException {
    public FragranceNotFoundException(String message) {
        super(message);
    }
}
