package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.ConcentrationNotFoundException;
import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.dto.ConcentrationDTO;
import com.rinseo.scentra.service.ConcentrationRepository;
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
    private final ConcentrationRepository repo;

    @GetMapping("/concentrations")
    public List<Concentration> getAllConcentrations() {
        return repo.findAll();
    }

    @GetMapping("/concentrations/{id}")
    public Concentration getConcentrationById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ConcentrationNotFoundException("Concentration with id " + id + " not found"));
    }

    @PutMapping("/concentrations/{id}")
    public ResponseEntity<ConcentrationDTO> update(@PathVariable long id, @Valid @RequestBody ConcentrationDTO concentration) {
        Concentration foundConcentration = repo.findById(id)
                .orElseThrow(() -> new ConcentrationNotFoundException("Concentration with id " + id + " not found"));
        foundConcentration.setName(concentration.name());
        foundConcentration.setDescription(concentration.description());

        Concentration updatedConcentration = repo.saveAndFlush(foundConcentration);

        return ResponseEntity.ok(new ConcentrationDTO(updatedConcentration.getName(), updatedConcentration.getDescription()));
    }

    @PostMapping("/concentrations")
    public ResponseEntity<Concentration> create(@Valid @RequestBody Concentration concentration) {
        Concentration savedConcentration = repo.saveAndFlush(concentration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedConcentration.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedConcentration);
    }

    @DeleteMapping("/concentrations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
