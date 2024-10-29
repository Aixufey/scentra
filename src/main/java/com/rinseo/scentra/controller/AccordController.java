package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Accord;
import com.rinseo.scentra.model.dto.AccordDTO;
import com.rinseo.scentra.service.AccordServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController
public class AccordController {
    private final AccordServiceImpl service;

    @GetMapping("/accords")
    public List<Accord> getAllAccords() {
        return service.getAll();
    }

    @GetMapping("/accords/{id}")
    public Accord getAccordById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/accords/{id}")
    public ResponseEntity<AccordDTO> update(@PathVariable long id, @Valid @RequestBody AccordDTO accord) {
        AccordDTO accordDTO = service.update(id, accord);

        return ResponseEntity
                .ok(accordDTO);
    }

    @PostMapping("/accords")
    public ResponseEntity<AccordDTO> create(@Valid @RequestBody AccordDTO accord) {
        AccordDTO accordDTO = service.create(accord);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accordDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(accordDTO);
    }

    @DeleteMapping("/accords/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
