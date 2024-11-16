package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BrandDTO(
        Long id,
        @NotBlank(message = "Brand name is required.") String name,
        @Nullable
        String imageUrl,
        @NotNull(message = "Country id is required.")
        Long countryId,
        @Nullable
        Long companyId) {
}
