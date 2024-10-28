package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Fragrance;
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
    public List<Fragrance> getAllFragrances() {
        return service.getAll();
    }

    @GetMapping("/v1/fragrances/{id}")
    public Fragrance getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/v1/fragrances/name/{name}")
    public List<Fragrance> getByName(@PathVariable String name) {
        return service.getByName(name);
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
}
