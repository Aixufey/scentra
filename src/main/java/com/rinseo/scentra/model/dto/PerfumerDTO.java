package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record PerfumerDTO(
        Long id,
        @NotBlank(message = "Perfumer name is required.") String name) {
}
