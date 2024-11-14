package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"perfumers", "brands"})
@EqualsAndHashCode(exclude = {"perfumers", "brands"})
@JsonIgnoreProperties(value = {"perfumers", "brands"})
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
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
//    @JsonManagedReference
    private Country country;

    // Bidirectional relationship with Perfumer
    // Retrieve perfumers that work for this company
    // Business logic allows company operations to impact perfumers
    // Cascade strategy is set to PERSIST and MERGE
    // Persist a new perfumer when a new company is created
    // Merge changes to the perfumer when the company is updated
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Perfumer> perfumers = new HashSet<>();

    // Bidirectional relationship with Brand
    // A company can own multiple brands
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Brand> brands = new HashSet<>();

    public Company(String name) {
        this.name = name;
    }
}
