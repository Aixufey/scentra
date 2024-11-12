package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@NoArgsConstructor
@Data
@Entity
@ToString(exclude = {"fragrances", "brands"})
@JsonIgnoreProperties(value = {"brands"})
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
    @JsonBackReference          // Prevents recursive serialization JSON
    @EqualsAndHashCode.Exclude  // Prevents collection recursion
    private Set<Fragrance> fragrances = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "perfumer_brand",
            joinColumns = @JoinColumn(name = "perfumer_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand> brands = new HashSet<>();    // Perfumer can work with multiple brands

    // Bidirectional relationship with Company
    // Many perfumers can work for one company
    // Owner of the relationship
    // Fetch eager for single entity
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonManagedReference
    private Company company;

    // Bidirectional relationship with Country
    // Many perfumers can be from one country
    // Owner of the relationship
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @JsonManagedReference
    private Country country;

    public Perfumer(String name) {
        this.name = name;
    }
}
