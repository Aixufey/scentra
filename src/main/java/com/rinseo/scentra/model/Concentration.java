package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Concentration implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concentration_id")
    private Long id;
    @NotBlank(message = "Concentration name is required.")
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "concentrations", fetch = FetchType.LAZY)
    @JsonBackReference // Prevents recursive serialization JSON
    private Set<Fragrance> fragrances = new HashSet<>();

    public Concentration(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
