package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;
import com.rinseo.scentra.service.CompanyServiceImpl;
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
public class CompanyController {
    private final CompanyServiceImpl service;

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return service.getAll();
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable long id, @Valid @RequestBody CompanyDTO company) {
        CompanyDTO companyDTO = service.update(id, company);

        return ResponseEntity
                .ok(companyDTO);
    }

    @PostMapping(value = "/companies", consumes = {"multipart/form-data"})
    public ResponseEntity<CompanyDTO> create(
            @Valid @RequestPart(value = "company") CompanyDTO company,
            @Nullable @RequestPart(value = "file") MultipartFile file) {
        CompanyDTO companyDTO = service.create(company, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(companyDTO.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(companyDTO);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
