package com.rinseo.scentra.utils;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileNameSanitizerTest {

    private FileNameSanitizer underTest;

    @BeforeEach
    void setUp() {
        underTest = new FileNameSanitizer();
    }

    @Test
    @DisplayName("Test file content type")
    void testContentType_WhenValidType_ReturnsTrue() {
        String fileType = "image/";
        boolean isValid = underTest.test(fileType);
        Assertions.assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Test file name")
    void testFileName_WhenInvalid_ReturnsTrue() {
        String fileName = "\\//helloWorld.jpg";
        String sanitizedName = underTest.sanitize(fileName);


        Assertions.assertThat(sanitizedName).isEqualTo("___helloWorld.jpg");
    }
}
