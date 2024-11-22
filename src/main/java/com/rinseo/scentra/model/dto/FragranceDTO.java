package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FragranceDTO {
    private Long id;
    @NotBlank(message = "Fragrance name is required.")
    private String name;
    @Min(value = 1900, message = "Year must be greater than 1900.")
    @Max(value = 2025, message = "Year must be less than 2026.")
    private int year;
    private String imageUrl;
    private Long brandId;
    private Long countryId;
    private Set<Long> perfumerIds;
    private Set<Long> concentrationIds;
    private Set<Long> noteIds;

    public FragranceDTO(long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }
}
