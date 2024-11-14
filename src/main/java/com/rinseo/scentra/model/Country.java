package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"perfumers", "fragrances", "companies", "brands"})
@EqualsAndHashCode(exclude = {"perfumers", "fragrances", "companies", "brands"})
@JsonIgnoreProperties(value = {"perfumers", "fragrances", "companies", "brands"})
public class Country implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;
    @Size(min = 4, message = "Country name must be at least 4 characters long.")
    @Column(unique = true)
    private String name;

    // One-to-many relationships
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Perfumer> perfumers = new HashSet<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Fragrance> fragrances = new HashSet<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Company> companies = new HashSet<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Brand> brands = new HashSet<>();

    public Country(String name) {
        this.name = name;
    }
}
