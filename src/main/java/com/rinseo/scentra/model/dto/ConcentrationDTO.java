package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ConcentrationDTO(
        Long id,
        @NotBlank(message = "Concentration name is required.") String name, String description) {
}
