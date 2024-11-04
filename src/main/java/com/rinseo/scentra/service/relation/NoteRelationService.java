package com.rinseo.scentra.service.relation;

import com.rinseo.scentra.model.Note;

import java.util.List;

public interface NoteRelationService {
    List<Note> getNotesRelation(long fragranceId);

    List<Note> updateNoteRelation(long fragranceId, long noteId);

    void deleteNoteRelation(long fragranceId, long noteId);
}
