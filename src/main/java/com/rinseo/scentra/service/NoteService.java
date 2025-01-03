package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.dto.NoteDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {
    Page<Note> getAll(int page, int size);

    List<Note> getAll();

    Note getById(long id);

    NoteDTO create(NoteDTO note);

    NoteDTO update(long id, NoteDTO note);

    void deleteById(long id);
}
