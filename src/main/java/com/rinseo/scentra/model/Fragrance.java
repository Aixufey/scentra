package com.rinseo.scentra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fragrance")
@JsonIgnoreProperties(value = {"perfumers", "concentrations", "notes"})
public class Fragrance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fragrance_id")
    private Long id;
    @NotBlank(message = "Fragrance name is required.")
    @Column(unique = true)
    private String name;
    @Min(value = 1900, message = "Year must be greater than 1900.")
    @Max(value = 2025, message = "Year must be less than 2026.")
    @Column(name = "\"year\"")
    private int year;

    // Bidirectional relationship with Brand
    // Cascade type is set to PERSIST and MERGE
    // Allows fragrance operations to cascade to the brand
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
//    @JsonManagedReference
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
//    @JsonManagedReference
    private Country country;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "fragrance_perfumer",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "perfumer_id")
    )
    private Set<Perfumer> perfumers = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "fragrance_concentration",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "concentration_id")
    )
    private Set<Concentration> concentrations = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "fragrance_notes",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    private Set<Note> notes = new HashSet<>();

    public Fragrance() {
    }

    public Fragrance(Long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Perfumer> getPerfumers() {
        return perfumers;
    }

    public void setPerfumers(Set<Perfumer> perfumers) {
        this.perfumers = perfumers;
    }

    public Set<Concentration> getConcentrations() {
        return concentrations;
    }

    public void setConcentrations(Set<Concentration> concentrations) {
        this.concentrations = concentrations;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Fragrance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", brand=" + brand +
                ", country=" + country +
                '}';
    }
}
