package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.service.FragranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class FragranceController {
    private final FragranceService fragranceService;

    public FragranceController(FragranceService fragranceService) {
        this.fragranceService = fragranceService;
    }

    @GetMapping("/v1/fragrances")
    public List<Fragrance> getAllFragrances() {
        return fragranceService.getAll();
    }

    @GetMapping("/v1/fragrances/{id}")
    public Fragrance getById(@PathVariable long id) {
        return fragranceService.getById(id);
    }

    @GetMapping("/v1/fragrances/name/{name}")
    public Fragrance getByName(@PathVariable String name) {
        return fragranceService.getByName(name);
    }

    @PostMapping("/v1/fragrances")
    public ResponseEntity<Fragrance> save(@RequestBody Fragrance fragrance) {
        Fragrance savedFragrance = fragranceService.save(fragrance);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFragrance.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedFragrance);
    }

    @PutMapping("/v1/fragrances/{id}")
    public Fragrance update(@PathVariable long id, @RequestBody Fragrance fragrance) {
        fragranceService.updateById(id, fragrance);
        return fragrance;
    }

    @DeleteMapping("/v1/fragrances/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        fragranceService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
