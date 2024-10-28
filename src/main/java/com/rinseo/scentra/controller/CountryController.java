package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import com.rinseo.scentra.service.CountryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController()
public class CountryController {
    private final CountryService service;

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
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable long id, @Valid @RequestBody CountryDTO country) {
        CountryDTO countryDTO = service.update(id, country);

        return ResponseEntity
                .ok(countryDTO);
    }

    @PostMapping("/countries")
    public ResponseEntity<CountryDTO> save(@Valid @RequestBody CountryDTO country) {
        CountryDTO countryDTO = service.create(country);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(countryDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(countryDTO);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
