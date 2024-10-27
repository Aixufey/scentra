package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record BrandDTO(@NotBlank(message = "Brand name is required.") String name) {
}
