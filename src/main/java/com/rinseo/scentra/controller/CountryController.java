package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import com.rinseo.scentra.service.CountryServiceImpl;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController()
public class CountryController {
    private final CountryServiceImpl service;

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return service.getAll();
    }

    @GetMapping("/countries/name/{name}")
    public Country getCountryByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable long id,
            @Valid @ModelAttribute CountryDTO country,
            @Nullable @RequestParam("file") MultipartFile file) {
        Country countryData = service.update(id, country, file);

        return ResponseEntity
                .ok(countryData);
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> save(
            @Valid @ModelAttribute CountryDTO country,
            @Nullable @RequestParam("file") MultipartFile file) {
        Country countryData = service.create(country, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(countryData.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(countryData);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
