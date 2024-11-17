package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CompanyDTO(
        Long id,
        @NotBlank(message = "Company name is required.") String name,
        @Nullable String imageUrl) {
}
