package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;
    @Size(min = 4, message = "Country name must be at least 4 characters long.")
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
