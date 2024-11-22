package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Long id;
    @NotBlank(message = "Brand name is required.")
    private String name;
    @Nullable
    private String imageUrl;
    @NotNull(message = "Country id is required.")
    private Long countryId;
    @Nullable
    private Long companyId;
}
