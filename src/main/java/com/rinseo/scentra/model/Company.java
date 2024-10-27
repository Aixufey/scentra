package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @NotBlank(message = "Company name is required.")
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Company(String name) {
        this.name = name;
    }
}
