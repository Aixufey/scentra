package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record PerfumerDTO(@NotBlank(message = "Perfumer name is required.") String name) {
}
