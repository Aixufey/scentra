package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.service.FragranceRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FragranceController {

    private final FragranceRepository repo;

    @Autowired
    public FragranceController(FragranceRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/v1/fragrances")
    public List<Fragrance> getAllFragrances() {
        return repo.findAll();
    }

    @GetMapping("/v1/fragrances/{id}")
    public Fragrance getById(@PathVariable long id) {
        return repo.findById(id).orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
    }

    @GetMapping("/v1/fragrances/name/{name}")
    public Fragrance getByName(@PathVariable String name) {
        return repo.findByNameEqualsIgnoreCase(name);
    }

    /**
     * Posting does not work with H2 because of returning the generated id is not supported.
     */
    @PostMapping("/v1/fragrances")
    public ResponseEntity<FragranceDTO> save(@Valid @RequestBody FragranceDTO fragrance) {
        // Convert DTO to Entity
        Fragrance fragranceDTO = new ModelMapper().map(fragrance, Fragrance.class);
        Fragrance savedFragrance = repo.saveAndFlush(fragranceDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFragrance.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new FragranceDTO(savedFragrance.getName(), savedFragrance.getYear()));
    }

    @PutMapping("/v1/fragrances/{id}")
    public ResponseEntity<FragranceDTO> update(@PathVariable long id, @Valid @RequestBody FragranceDTO fragrance) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.name());
        foundFragrance.setYear(fragrance.year());
        Fragrance updatedFragrance = repo.saveAndFlush(foundFragrance);

        var updatedFragranceDTO = new FragranceDTO(updatedFragrance.getName(), updatedFragrance.getYear());
        return ResponseEntity.ok(updatedFragranceDTO);
    }

    @DeleteMapping("/v1/fragrances/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
