package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.exception.NoteNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.repository.FragranceRepository;
import com.rinseo.scentra.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FragranceNoteServiceImpl implements FragranceNoteService {
    private final FragranceRepository fragranceRepository;
    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getNotes() == null) {
            throw new NoteNotFoundException("Notes not found for fragrance with id " + fragranceId);
        }

        return List.copyOf(fragrance.getNotes());
    }

    @Override
    public List<Note> updateNotes(long fragranceId, List<Long> noteIds) {
        Fragrance fragrance = getFragrance(fragranceId);

        for (Long id : noteIds) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid note id: " + id);
            }
        }

        List<Note> foundNotes = noteRepository.findAllById(noteIds);

        Set<Note> notes = fragrance.getNotes();
        notes.addAll(foundNotes);
        fragrance.setNotes(notes);

        for (var note : foundNotes) {
            Set<Fragrance> fragrances = note.getFragrances();
            fragrances.add(fragrance);
            note.setFragrances(fragrances);
        }

        noteRepository.saveAllAndFlush(foundNotes);
        fragranceRepository.saveAndFlush(fragrance);

        return List.copyOf(notes);
    }

    @Override
    @Transactional
    public void deleteNote(long fragranceId, long noteId) {
        Fragrance fragrance = getFragrance(fragranceId);

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + noteId + " not found"));

        Set<Note> notes = fragrance.getNotes();
        notes.remove(note);
        fragrance.setNotes(notes);

        Set<Fragrance> fragrances = note.getFragrances();
        fragrances.remove(fragrance);
        note.setFragrances(fragrances);

        noteRepository.saveAndFlush(note);
        fragranceRepository.saveAndFlush(fragrance);
    }

    @Override
    @Transactional
    public void deleteAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        Set<Note> notes = fragrance.getNotes();
        for (var note : notes) {
            Set<Fragrance> fragrances = note.getFragrances();
            fragrances.remove(fragrance);
            note.setFragrances(fragrances);
        }

        fragrance.getNotes().clear();

        noteRepository.saveAllAndFlush(notes);
        fragranceRepository.saveAndFlush(fragrance);
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + fragranceId + " not found"));
    }
}
