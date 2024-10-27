package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.Size;

public record CountryDTO(
        @Size(min = 4, message = "Country name must be at least 4 characters long.")
        String name) {
}
