package com.rinseo.scentra.configuration;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CloudinaryConfigTest {
    private CloudinaryConfig cloudinaryConfig;

    @BeforeEach
    void setUp() {
        cloudinaryConfig = new CloudinaryConfig();
    }

    @Test
    @DisplayName("Test if system environment variable is set and returns true")
    void testSysEnv_WhenSet_ReturnsTrue() {
        String env = cloudinaryConfig.getSystemEnv("CLOUDINARY_URL");

        Assertions.assertNotEquals(null, env);
    }

    @Test
    @DisplayName("Test Cloudinary API key when invalid throws exception")
    void testCloudinaryAPIKey_WhenInvalidKey_ShouldThrowException() {

        RuntimeException whenInvalid = Assertions.assertThrows(RuntimeException.class, () -> {
            cloudinaryConfig.getCloudinary("");
        });

        Assertions.assertEquals("Could not find Cloudinary API key from system environment or .env file", whenInvalid.getMessage());
    }

    @Test
    @DisplayName("Test Cloudinary API key when valid returns Cloudinary instance")
    void testCloudinaryAPIKey_WhenValidKey_ReturnsCloudinaryInstance() {
        Cloudinary cloudinary = cloudinaryConfig.getCloudinary("CLOUDINARY_URL");

        Assertions.assertNotEquals(null, cloudinary);
    }
}
