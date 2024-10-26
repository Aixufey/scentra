package com.rinseo.scentra.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Perfumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfumer_id")
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "perfumer_brand",
            joinColumns = @JoinColumn(name = "perfumer_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand> brand;
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
