package com.rinseo.scentra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;
    @NotBlank(message = "Note name is required.")
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "accord_id")
    private Accord accord;

    public Note(String name, String description, Accord accord) {
        this.name = name;
        this.description = description;
        this.accord = accord;
    }
}
