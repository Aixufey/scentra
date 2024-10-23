package com.rinseo.scentra.model;

import jakarta.persistence.*;

@Entity
public class Concentration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concentration_id")
    private Long id;
    private String name;
    private String description;

    public Concentration() {
    }

    public Concentration(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Concentration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
