package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
