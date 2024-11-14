package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.model.Note;

import java.util.List;

public interface FragranceNoteService {
    List<Note> getAll(long fragranceId);

    List<Note> updateNotes(long fragranceId, List<Long> noteIds);

    void deleteNote(long fragranceId, long noteId);

    void deleteAll(long fragranceId);
}
