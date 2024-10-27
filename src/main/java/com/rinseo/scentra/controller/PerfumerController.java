package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.service.PerfumerRepository;
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
    private PerfumerRepository repo;

    @GetMapping("/perfumers")
    public List<Perfumer> getAllPerfumers() {
        log.info("Fetching all perfumers");
        return repo.findAll();
    }

    @GetMapping("/perfumers/{id}")
    public Perfumer getPerfumerById(@PathVariable long id) {
        return repo.findById(id).orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
    }

    @GetMapping("/perfumers/name/{name}")
    public List<Perfumer> getPerfumersByName(@PathVariable String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("/perfumers/{id}")
    public ResponseEntity<PerfumerDTO> update(@PathVariable long id, @RequestBody PerfumerDTO perfumer) {
        Perfumer foundPerfumer = repo.findById(id)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        foundPerfumer.setName(perfumer.name());
        Perfumer updatedPerfumer = repo.saveAndFlush(foundPerfumer);

        return ResponseEntity
                .ok(new PerfumerDTO(updatedPerfumer.getName()));
    }


    @PostMapping("/perfumers")
    public ResponseEntity<Perfumer> save(@RequestBody Perfumer perfumer) {
        Perfumer savedPerfumer = repo.saveAndFlush(perfumer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerfumer.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedPerfumer);
    }
}
