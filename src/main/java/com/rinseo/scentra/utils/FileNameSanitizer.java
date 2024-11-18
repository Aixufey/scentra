package com.rinseo.scentra.utils;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class FileNameSanitizer implements Predicate<Object> {

    @Override
    public boolean test(Object o) {
        return o.toString().startsWith("image/");
    }

    public String sanitize(String fileName) {
        if (fileName.length() > 100) {
            throw new IllegalArgumentException("File name is too long");
        }

        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
