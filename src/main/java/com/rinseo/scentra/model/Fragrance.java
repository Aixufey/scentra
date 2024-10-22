package com.rinseo.scentra.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Fragrance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "\"year\"")
    private int year;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToMany
    @JoinTable(
            name = "fragrance_perfumer",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "perfumer_id")
    )
    private Set<Perfumer> perfumers;
    @ManyToMany
    @JoinTable(
            name = "fragrance_concentration",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "concentration_id")
    )
    private Set<Concentration> concentrations;

    @ManyToMany
    @JoinTable(
            name = "fragrance_notes",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    private Set<Note> notes;

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
                '}';
    }
}
