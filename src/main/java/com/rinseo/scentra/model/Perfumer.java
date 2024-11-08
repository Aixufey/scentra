package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Perfumer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfumer_id")
    private Long id;
    @NotBlank(message = "Perfumer name is required.")
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "perfumers", fetch = FetchType.LAZY)
    @JsonBackReference // Prevents recursive serialization JSON
    private Set<Fragrance> fragrances = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "perfumer_brand",
            joinColumns = @JoinColumn(name = "perfumer_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    @JsonIgnore
    private Set<Brand> brands = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Perfumer(String name) {
        this.name = name;
    }
}
