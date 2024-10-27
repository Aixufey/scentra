package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.AccordNotFoundException;
import com.rinseo.scentra.model.Accord;
import com.rinseo.scentra.model.dto.AccordDTO;
import com.rinseo.scentra.service.AccordRepository;
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
    private final AccordRepository repo;

    @GetMapping("/accords")
    public List<Accord> getAllAccords() {
        return repo.findAll();
    }

    @GetMapping("/accords/{id}")
    public Accord getAccordById(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new AccordNotFoundException("Accord not found with id: " + id));
    }

    @PutMapping("/accords/{id}")
    public ResponseEntity<AccordDTO> update(@PathVariable long id, @RequestBody AccordDTO accord) {
        Accord foundAccord = repo.findById(id)
                .orElseThrow(() -> new AccordNotFoundException("Accord not found with id: " + id));

        foundAccord.setName(accord.name());
        foundAccord.setDescription(accord.description());
        Accord updatedAccord = repo.saveAndFlush(foundAccord);

        return ResponseEntity.ok(new AccordDTO(updatedAccord.getName(), updatedAccord.getDescription()));
    }

    @PostMapping("/accords")
    public ResponseEntity<Accord> create(@RequestBody Accord accord) {
        Accord savedAccord = repo.saveAndFlush(accord);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAccord.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(savedAccord);
    }

    @DeleteMapping("/accords/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
