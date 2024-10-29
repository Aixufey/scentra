package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AccordDTO(
        Long id,
        @NotBlank(message = "Accord name is required.") String name,
        String description) {
}
