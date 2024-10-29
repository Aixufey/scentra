package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyDTO(
        Long id,
        @NotBlank(message = "Company name is required.") String name) {
}
