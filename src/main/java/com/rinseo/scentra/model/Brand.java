package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Brand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;
    @NotBlank(message = "Brand name is required.")
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
    @JsonBackReference // Prevents recursive serialization JSON
    private Set<Perfumer> perfumers = new HashSet<>();

    public Brand(String name) {
        this.name = name;
    }
}
