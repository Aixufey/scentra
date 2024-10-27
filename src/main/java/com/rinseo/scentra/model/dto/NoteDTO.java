package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteDTO(@NotBlank(message = "Note name is required.") String name, String description) {
}
