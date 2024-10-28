package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record FragranceDTO(
        Long id,
        @NotBlank(message = "Fragrance name is required.")
        String name,
        @Min(value = 1900, message = "Year must be greater than 1900.")
        @Max(value = 2025, message = "Year must be less than 2026.") int year) {
}
