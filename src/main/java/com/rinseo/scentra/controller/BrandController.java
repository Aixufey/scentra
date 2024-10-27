package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.BrandNotFoundException;
import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;
import com.rinseo.scentra.service.BrandRepository;
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
public class BrandController {
    private final BrandRepository repo;

    @GetMapping("/brands")
    public List<Brand> getAllBrands() {
        return repo.findAll();
    }

    @GetMapping("/brands/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand by id " + id + " was not found"));
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> update(@PathVariable long id, @Valid @RequestBody BrandDTO brand) {
        Brand foundBrand = repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand by id " + id + " was not found"));
        foundBrand.setName(brand.name());

        Brand updatedBrand = repo.saveAndFlush(foundBrand);
        return ResponseEntity.ok(new BrandDTO(updatedBrand.getName()));
    }

    @PostMapping("/brands")
    public ResponseEntity<Brand> create(@Valid @RequestBody Brand brand) {
        Brand newBrand = repo.saveAndFlush(brand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBrand.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(newBrand);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
