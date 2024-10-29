package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record BrandDTO(
        Long id,
        @NotBlank(message = "Brand name is required.") String name) {
}
