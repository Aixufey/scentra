package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;
import com.rinseo.scentra.service.BrandServiceImpl;
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
    private final BrandServiceImpl service;

    @GetMapping("/brands")
    public List<Brand> getAllBrands() {
        return service.getAll();
    }

    @GetMapping("/brands/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> update(@PathVariable long id, @Valid @RequestBody BrandDTO brand) {
        Brand brandDTO = service.update(id, brand);

        return ResponseEntity
                .ok(brandDTO);
    }

    @PostMapping("/brands")
    public ResponseEntity<Brand> create(@Valid @RequestBody BrandDTO brand) {
        Brand brandDTO = service.create(brand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(brandDTO.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(brandDTO);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
