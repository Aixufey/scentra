package com.rinseo.scentra.model;

import jakarta.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "accord_id")
    private Accord accord;

    public Note() {
    }

    public Note(Long id, String name, String description, Accord accord) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accord = accord;
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

    public Accord getAccord() {
        return accord;
    }

    public void setAccord(Accord accord) {
        this.accord = accord;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
