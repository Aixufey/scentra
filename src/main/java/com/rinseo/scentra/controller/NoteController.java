package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.dto.NoteDTO;
import com.rinseo.scentra.service.NoteServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController
public class NoteController {
    private final NoteServiceImpl service;

    // Pagination with JPA
    // Example: 30 items, 5 items per page -> 6 pages
    // /notes?page=0&size=5
    // Max size is 20 items per page enforced by the controller
    // https://docs.spring.io/spring-data/commons/reference/repositories/core-extensions.html#core.web.pageables
    // Spring recommends using PagedModel instead of Page to structure JSON response as Page
    @GetMapping("/notes")
    public PagedModel<Note> getNotes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return new PagedModel<>(service.getAll(page, size));
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable long id, @Valid @RequestBody NoteDTO note) {
        NoteDTO noteDTO = service.update(id, note);

        return ResponseEntity
                .ok(noteDTO);
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO note) {
        NoteDTO noteDTO = service.create(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(noteDTO.id())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(noteDTO);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
