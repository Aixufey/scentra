package com.rinseo.scentra.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private Long id;
    @Size(min = 4, message = "Country name must be at least 4 characters long.")
    private String name;
    private String imageUrl;
}
