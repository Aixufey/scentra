package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;
import com.rinseo.scentra.service.BrandServiceImpl;
import com.rinseo.scentra.utils.EntityTransformer;
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
@RestController
public class BrandController {
    private final BrandServiceImpl service;
    private final EntityTransformer entityTransformer;

    @GetMapping("/brands")
    public List<Brand> getAllBrands() {
        return service.getAll();
    }

    @GetMapping("/brands/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> update(
            @PathVariable long id,
            @Valid @ModelAttribute BrandDTO brand,
            @Nullable @RequestPart(value = "file") MultipartFile file) {
        Brand brandDTO = service.update(id, brand, file);
        Brand mapped = entityTransformer.mapEntityUrl(brandDTO, Brand.class);

        return ResponseEntity
                .ok(mapped);
    }

    @PostMapping("/brands")
    public ResponseEntity<Brand> create(
            @Valid @ModelAttribute BrandDTO brand,
            @Nullable @RequestPart(value = "file") MultipartFile file) {
        Brand brandDTO = service.create(brand, file);
        Brand mapped = entityTransformer.mapEntityUrl(brandDTO, Brand.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mapped.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(mapped);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
