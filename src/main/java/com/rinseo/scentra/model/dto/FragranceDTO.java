package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record FragranceDTO(@NotBlank(message = "Fragrance name is required.") String name, int year) {
}
