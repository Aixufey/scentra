package com.rinseo.scentra.model;

import java.util.List;

public class Fragrance {
    private Long id;
    private String name;
    private int year;
    private List<Note> notes;

    public Fragrance() {
    }

    public Fragrance(Long id, String name, int year, List<Note> notes) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Fragrance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", notes=" + notes +
                '}';
    }
}
