package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteDTO(
        Long id,
        @NotBlank(message = "Note name is required.") String name, String description) {
}
