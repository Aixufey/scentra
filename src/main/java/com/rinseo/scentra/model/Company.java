package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @NotBlank(message = "Company name is required.")
    @Column(unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Company(String name) {
        this.name = name;
    }
}
