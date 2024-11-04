package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.service.FragranceServiceV2;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FragranceController {
    private final FragranceServiceV2 service;

    @Autowired
    public FragranceController(FragranceServiceV2 service) {
        this.service = service;
    }

    @GetMapping("/v1/fragrances")
    public ResponseEntity<List<Fragrance>> getAllFragrances() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/v1/fragrances/{id}")
    public ResponseEntity<Fragrance> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/v1/fragrances/name/{name}")
    public ResponseEntity<List<Fragrance>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    /**
     * Posting does not work with H2 because of returning the generated id is not supported.
     */
    @PostMapping("/v1/fragrances")
    public ResponseEntity<FragranceDTO> save(@Valid @RequestBody FragranceDTO fragrance) {
        FragranceDTO fragranceDTO = service.create(fragrance);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fragranceDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(fragranceDTO);
    }

    @PutMapping("/v1/fragrances/{id}")
    public ResponseEntity<FragranceDTO> update(@PathVariable long id, @Valid @RequestBody FragranceDTO fragrance) {
        FragranceDTO fragranceDTO = service.update(id, fragrance);

        return ResponseEntity.ok(fragranceDTO);
    }

    @DeleteMapping("/v1/fragrances/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/brand/{brandId}")
    public ResponseEntity<Fragrance> updateBrandRelation(@PathVariable long fragranceId, @PathVariable long brandId) {
        Fragrance fragrance = service.updateBrandRelation(fragranceId, brandId);

        return ResponseEntity.ok(fragrance);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/brand/{brandId}")
    public ResponseEntity<Void> deleteBrandRelation(@PathVariable long fragranceId, @PathVariable long brandId) {
        service.deleteBrandRelation(fragranceId, brandId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/country/{countryId}")
    public ResponseEntity<Fragrance> updateCountryRelation(@PathVariable long fragranceId, @PathVariable long countryId) {
        Fragrance fragrance = service.updateCountryRelation(fragranceId, countryId);

        return ResponseEntity.ok(fragrance);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/country/{countryId}")
    public ResponseEntity<Void> deleteCountryRelation(@PathVariable long fragranceId, @PathVariable long countryId) {
        service.deleteCountryRelation(fragranceId, countryId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/v1/fragrances/{fragranceId}/perfumers")
    public ResponseEntity<List<Perfumer>> getPerfumers(@PathVariable long fragranceId) {
        List<Perfumer> perfumers = service.getPerfumersRelation(fragranceId);

        return ResponseEntity.ok(perfumers);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/perfumers/{perfumerId}")
    public ResponseEntity<List<Perfumer>> updatePerfumers(@PathVariable long fragranceId, @PathVariable long perfumerId) {
        List<Perfumer> perfumers = service.updatePerfumerRelation(fragranceId, perfumerId);

        return ResponseEntity.ok(perfumers);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/perfumers/{perfumerId}")
    public ResponseEntity<Void> deletePerfumer(@PathVariable long fragranceId, @PathVariable long perfumerId) {
        service.deletePerfumerRelation(fragranceId, perfumerId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/v1/fragrances/{fragranceId}/concentrations")
    public ResponseEntity<List<Concentration>> getConcentrations(@PathVariable long fragranceId) {
        List<Concentration> concentrations = service.getConcentrationsRelation(fragranceId);

        return ResponseEntity.ok(concentrations);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/concentrations/{concentrationId}")
    public ResponseEntity<List<Concentration>> updateConcentrations(@PathVariable long fragranceId, @PathVariable long concentrationId) {
        List<Concentration> concentrations = service.updateConcentrationRelation(fragranceId, concentrationId);

        return ResponseEntity.ok(concentrations);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/concentrations/{concentrationId}")
    public ResponseEntity<Void> deleteConcentration(@PathVariable long fragranceId, @PathVariable long concentrationId) {
        service.deleteConcentrationRelation(fragranceId, concentrationId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/v1/fragrances/{fragranceId}/notes")
    public ResponseEntity<List<Note>> getNotes(@PathVariable long fragranceId) {
        List<Note> notes = service.getNotesRelation(fragranceId);

        return ResponseEntity.ok(notes);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/notes/{noteId}")
    public ResponseEntity<List<Note>> updateNotes(@PathVariable long fragranceId, @PathVariable long noteId) {
        List<Note> notes = service.updateNoteRelation(fragranceId, noteId);

        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long fragranceId, @PathVariable long noteId) {
        service.deleteNoteRelation(fragranceId, noteId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
