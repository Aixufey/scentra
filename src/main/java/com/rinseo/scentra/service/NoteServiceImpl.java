package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.NoteNotFoundException;
import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.dto.NoteDTO;
import com.rinseo.scentra.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository repo;

    @Override
    public Page<Note> getAll(int page, int size) {
        int maxSize = 20;
        if (size > maxSize) {
            size = maxSize;
        }
        return repo.findAll(PageRequest.of(page, size));
    }

    @Override
    public Note getById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id " + id));
    }

    @Override
    public NoteDTO create(NoteDTO note) {
        Note noteEntity = new ModelMapper().map(note, Note.class);
        Note savedNote = repo.saveAndFlush(noteEntity);

        return new NoteDTO(savedNote.getId(), savedNote.getName(), savedNote.getDescription());
    }

    @Override
    public NoteDTO update(long id, NoteDTO note) {
        Note foundNote = repo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id " + id));
        foundNote.setName(note.name());
        foundNote.setDescription(note.description());

        Note updatedNote = repo.saveAndFlush(foundNote);

        return new NoteDTO(updatedNote.getId(), updatedNote.getName(), updatedNote.getDescription());
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }
}
