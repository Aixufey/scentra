package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record FragranceDTO(
        Long id,
        @NotBlank(message = "Fragrance name is required.")
        String name,
        @Min(value = 1900, message = "Year must be greater than 1900.")
        @Max(value = 2025, message = "Year must be less than 2026.") int year,
        @Nullable
        Long brandId,
        @Nullable
        Long countryId,
        @Nullable
        Set<Long> perfumerIds,
        @Nullable
        Set<Long> concentrationIds,
        @Nullable
        Set<Long> noteIds) {
    public FragranceDTO(Long id, String name, int year) {
        this(id, name, year, null, null, null, null, null);
    }
}
