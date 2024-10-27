package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Accord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accord_id")
    private Long id;
    @NotBlank(message = "Accord name is required.")
    private String name;
    private String description;


    public Accord(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
