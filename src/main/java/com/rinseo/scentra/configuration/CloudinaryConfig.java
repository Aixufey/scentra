package com.rinseo.scentra.configuration;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    public String getSystemEnv(String var) {
        return System.getenv(var);
    }

    /**
     * Get Cloudinary instance by providing the environment variable name
     * It will attempt to get the API key from .env file during development
     * otherwise it will get the API key from system environment variable in production
     * @param variable The environment variable name
     * @return Cloudinary instance
     */
    public Cloudinary getCloudinary(String variable) {
        // Check first for System environment variable
        String cloudinaryUrl = getSystemEnv(variable);
        // If not found, check for .env file
        if (cloudinaryUrl == null) {
            try {
                cloudinaryUrl = Dotenv.load().get(variable);
            } catch (Exception e) {
                throw new RuntimeException("Could not find Cloudinary API key from system environment or .env file");
            }
        }
        // If both are not found, throw exception
        if (cloudinaryUrl == null) {
            throw new RuntimeException("Could not find Cloudinary API key from system environment or .env file");
        }

        try {
            return new Cloudinary(cloudinaryUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Cloudinary instance");
        }
    }
}
