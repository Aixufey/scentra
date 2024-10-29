package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.dto.ConcentrationDTO;
import com.rinseo.scentra.service.ConcentrationServiceImpl;
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
public class ConcentrationController {
    private final ConcentrationServiceImpl service;

    @GetMapping("/concentrations")
    public List<Concentration> getAllConcentrations() {
        return service.getAll();
    }

    @GetMapping("/concentrations/{id}")
    public Concentration getConcentrationById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/concentrations/{id}")
    public ResponseEntity<ConcentrationDTO> update(@PathVariable long id, @Valid @RequestBody ConcentrationDTO concentration) {
        ConcentrationDTO concentrationDTO = service.update(id, concentration);

        return ResponseEntity
                .ok(concentrationDTO);
    }

    @PostMapping("/concentrations")
    public ResponseEntity<ConcentrationDTO> create(@Valid @RequestBody ConcentrationDTO concentration) {
        ConcentrationDTO concentrationDTO = service.create(concentration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(concentrationDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(concentrationDTO);
    }

    @DeleteMapping("/concentrations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
