package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import com.rinseo.scentra.service.CountryRepository;
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
    private final CountryRepository repo;

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return repo.findAll();
    }

    @GetMapping("/countries/name/{name}")
    public Country getCountryByName(@PathVariable String name) {
        return repo.findCountryByNameEqualsIgnoreCase(name);
    }

    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable long id, @RequestBody CountryDTO country) {
        Country foundCountry = repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
        foundCountry.setName(country.name());
        Country updatedCountry = repo.saveAndFlush(foundCountry);

        return ResponseEntity.ok(new CountryDTO(updatedCountry.getName()));
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> save(@RequestBody Country country) {
        Country savedCountry = repo.saveAndFlush(country);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCountry.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(savedCountry);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
