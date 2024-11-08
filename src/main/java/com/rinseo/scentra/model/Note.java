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

@NoArgsConstructor
@Data
@Entity
public class Note implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;
    @NotBlank(message = "Note name is required.")
    @Column(unique = true)
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "accord_id")
    private Accord accord;

    @ManyToMany(mappedBy = "notes", fetch = FetchType.LAZY)
    @JsonBackReference // Prevents recursive serialization JSON
    private Set<Fragrance> fragrances = new HashSet<>();

    public Note(String name, String description, Accord accord) {
        this.name = name;
        this.description = description;
        this.accord = accord;
    }
}
