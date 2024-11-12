package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"perfumers", "fragrances"})
@EqualsAndHashCode(exclude = {"perfumers", "fragrances"})
public class Brand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;
    @NotBlank(message = "Brand name is required.")
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @JsonManagedReference
    private Country country;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonManagedReference
    private Company company;

    @ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Perfumer> perfumers = new HashSet<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Fragrance> fragrances = new HashSet<>();

    public Brand(String name) {
        this.name = name;
    }
}
