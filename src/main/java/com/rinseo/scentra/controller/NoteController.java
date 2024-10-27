package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.NoteNotFoundException;
import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.dto.NoteDTO;
import com.rinseo.scentra.service.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController
public class NoteController {
    private final NoteRepository repo;

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return repo.findAll();
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id " + id));
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable long id, @RequestBody NoteDTO note) {
        Note foundNote = repo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id " + id));
        foundNote.setName(note.name());
        foundNote.setDescription(note.description());
        Note updatedNote = repo.saveAndFlush(foundNote);

        return ResponseEntity.ok(new NoteDTO(updatedNote.getName(), updatedNote.getDescription()));
    }

    @PostMapping("/notes")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        Note savedNote = repo.saveAndFlush(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(savedNote);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
