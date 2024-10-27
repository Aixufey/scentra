package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Concentration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concentration_id")
    private Long id;
    @NotBlank(message = "Concentration name is required.")
    private String name;
    private String description;

    public Concentration(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
