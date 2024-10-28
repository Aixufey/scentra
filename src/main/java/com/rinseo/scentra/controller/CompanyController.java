package com.rinseo.scentra.controller;

import com.rinseo.scentra.exception.CompanyNotFoundException;
import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;
import com.rinseo.scentra.repository.CompanyRepository;
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
public class CompanyController {
    private final CompanyRepository repo;

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return repo.findAll();
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable long id, @Valid @RequestBody CompanyDTO company) {
        Company foundCompany = repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
        foundCompany.setName(company.name());

        Company updatedCompany = repo.saveAndFlush(foundCompany);

        return ResponseEntity.ok(new CompanyDTO(updatedCompany.getName()));
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> create(@Valid @RequestBody Company company) {
        Company savedCompany = repo.saveAndFlush(company);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCompany.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
