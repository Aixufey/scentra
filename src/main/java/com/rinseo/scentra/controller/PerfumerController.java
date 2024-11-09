package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.service.PerfumerServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
@RestController
public class PerfumerController {
    private PerfumerServiceImpl service;

    @GetMapping("/perfumers")
    public ResponseEntity<List<Perfumer>> getAllPerfumers() {
        log.info("Fetching all perfumers");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/perfumers/{id}")
    public ResponseEntity<Perfumer> getPerfumerById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/perfumers/query")
    public ResponseEntity<List<Perfumer>> getPerfumersByName(@RequestParam(required = false) String name) {
        if (name != null) {
            String sanitizedName = name.replaceAll("[^a-zA-Z0-9 ]", "");
            log.info("Sanitized name: {}", sanitizedName);
            return ResponseEntity.ok(service.getByName(sanitizedName));
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }

    @PutMapping("/perfumers/{id}")
    public ResponseEntity<PerfumerDTO> update(@PathVariable long id, @Valid @RequestBody PerfumerDTO perfumer) {
        PerfumerDTO perfumerDTO = service.update(id, perfumer);

        return ResponseEntity
                .ok(perfumerDTO);
    }


    @PostMapping("/perfumers")
    public ResponseEntity<PerfumerDTO> save(@Valid @RequestBody PerfumerDTO perfumer) {
        PerfumerDTO perfumerDTO = service.create(perfumer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(perfumerDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(perfumerDTO);
    }

    @DeleteMapping("/perfumers/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/perfumers/{id}/fragrances")
    public ResponseEntity<List<Fragrance>> getFragranceRelation(@PathVariable long id) {
        List<Fragrance> fragrancesRelation = service.getFragrancesRelation(id);
        return ResponseEntity.ok(fragrancesRelation);

    }

    @PutMapping("/perfumers/{id}/fragrances/{fragranceId}")
    public ResponseEntity<List<Fragrance>> updateFragranceRelation(@PathVariable long id, @PathVariable long fragranceId) {
        List<Fragrance> fragrancesRelation = service.updateFragranceRelation(id, fragranceId);

        return ResponseEntity.ok(fragrancesRelation);
    }

    @DeleteMapping("/perfumers/{id}/fragrances/{fragranceId}")
    public ResponseEntity<Void> deleteFragranceRelation(@PathVariable long id, @PathVariable long fragranceId) {
        service.deleteFragranceRelation(id, fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
