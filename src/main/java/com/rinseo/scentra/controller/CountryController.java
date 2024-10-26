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

@AllArgsConstructor
@RestController()
public class CountryController {
    private final CountryRepository repo;

    @GetMapping("/v1/countries")
    public List<Country> getAllCountries() {
        return repo.findAll();
    }

    @GetMapping("/v1/countries/name/{name}")
    public Country getCountryByName(@PathVariable String name) {
        return repo.findCountryByNameEqualsIgnoreCase(name);
    }

    @GetMapping("/v1/countries/{id}")
    public Country getCountryById(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
    }

    @PutMapping("/v1/countries/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable long id, @RequestBody CountryDTO country) {
        Country foundCountry = repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
        foundCountry.setName(country.name());
        Country updatedCountry = repo.saveAndFlush(foundCountry);

        return ResponseEntity.ok(new CountryDTO(updatedCountry.getName()));
    }

    @PostMapping("/v1/countries")
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

    @DeleteMapping("/v1/countries/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
