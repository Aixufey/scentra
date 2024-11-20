package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PerfumerDTO {
    private Long id;
    @NotBlank(message = "Perfumer name is required.")
    private String name;
    @Nullable
    private String imageUrl;
    @Nullable
    private Long companyId;
    @Nullable
    private Long countryId;
    @Nullable
    private Set<Long> fragranceIds;
    @Nullable
    private Set<Long> brandIds;

    public PerfumerDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
