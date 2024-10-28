package com.rinseo.scentra.controller;

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
    public List<Perfumer> getAllPerfumers() {
        log.info("Fetching all perfumers");
        return service.getAll();
    }

    @GetMapping("/perfumers/{id}")
    public Perfumer getPerfumerById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/perfumers/name/{name}")
    public List<Perfumer> getPerfumersByName(@PathVariable String name) {
        return service.getByName(name);
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
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }
}
