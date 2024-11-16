package com.rinseo.scentra.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PerfumerDTO(
        Long id,
        @NotBlank(message = "Perfumer name is required.") String name,
        @Nullable
        String imageUrl,
        @Nullable
        Long companyId,
        @Nullable
        Long countryId,
        @Nullable
        Set<Long> fragranceIds,
        @Nullable
        Set<Long> brandIds) {
        public PerfumerDTO(long id, String name) {
                this(id, name, null, null, null, null, null);
        }
}
