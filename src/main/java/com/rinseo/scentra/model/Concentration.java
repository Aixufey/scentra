package com.rinseo.scentra.model;

import jakarta.persistence.*;
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
    private String name;
    private String description;

    public Concentration(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
