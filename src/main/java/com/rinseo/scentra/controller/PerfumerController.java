package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.service.PerfumerServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerBrandServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCompanyServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCountryServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerFragranceServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
@RestController
public class PerfumerController {
    private PerfumerServiceImpl service;
    private PerfumerFragranceServiceImpl perfumerFragranceService;
    private PerfumerBrandServiceImpl perfumerBrandService;
    private PerfumerCountryServiceImpl perfumerCountryService;
    private PerfumerCompanyServiceImpl perfumerCompanyService;

    @GetMapping("/perfumers")
    public ResponseEntity<List<Perfumer>> getAllPerfumers() {
        log.info("Fetching all perfumers");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/perfumers/{id}")
    public ResponseEntity<Perfumer> getPerfumerById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/perfumers/query")
    public ResponseEntity<List<Perfumer>> getPerfumersByName(@RequestParam(required = false) String name) {
        if (name != null) {
            String sanitizedName = name.replaceAll("[^a-zA-Z0-9 ]", "");
            log.info("Sanitized name: {}", sanitizedName);
            return ResponseEntity.ok(service.getByName(sanitizedName));
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }

    @PutMapping("/perfumers/{id}")
    public ResponseEntity<Perfumer> update(@PathVariable long id, @Valid @RequestBody PerfumerDTO perfumer) {
        Perfumer perfumerDTO = service.update(id, perfumer);

        return ResponseEntity
                .ok(perfumerDTO);
    }


    @PostMapping("/perfumers")
    public ResponseEntity<Perfumer> save(@Valid @RequestBody PerfumerDTO perfumer) {
        Perfumer perfumerDTO = service.create(perfumer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(perfumerDTO.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(perfumerDTO);
    }

    @DeleteMapping("/perfumers/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    /////////////////////////// FRAGRANCE INVERSE RELATIONSHIP ///////////////////////////
    @GetMapping("/perfumers/{id}/fragrances")
    public ResponseEntity<List<Fragrance>> getPerfumerFragrances(@PathVariable long id) {
        List<Fragrance> fragrancesRelation = perfumerFragranceService.getAll(id);
        return ResponseEntity.ok(fragrancesRelation);

    }

    @PutMapping("/perfumers/{id}/fragrances")
    public ResponseEntity<List<Fragrance>> updatePerfumerFragrances(@PathVariable long id, @RequestBody List<Long> fragranceIds) {
        List<Fragrance> fragrances = perfumerFragranceService.updateFragrances(id, fragranceIds);

        return ResponseEntity.ok(fragrances);
    }

    @DeleteMapping("/perfumers/{id}/fragrances/{fragranceId}")
    public ResponseEntity<Void> deleteFragranceRelation(@PathVariable long id, @PathVariable long fragranceId) {
        perfumerFragranceService.deleteFragrance(id, fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/perfumers/{id}/fragrances")
    public ResponseEntity<Void> deleteAllFragranceRelation(@PathVariable long id) {
        perfumerFragranceService.deleteAll(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    /////////////////////////// BRAND INVERSE RELATIONSHIP ///////////////////////////
    @GetMapping("/perfumers/{id}/brands")
    public ResponseEntity<List<Brand>> getPerfumerBrands(@PathVariable long id) {
        List<Brand> brandsRelation = perfumerBrandService.getAll(id);

        return ResponseEntity.ok(brandsRelation);
    }

    @PutMapping("/perfumers/{id}/brands/{brandId}")
    public ResponseEntity<List<Brand>> updatePerfumerBrands(@PathVariable long id, @PathVariable long brandId) {
        List<Brand> brands = perfumerBrandService.updateBrand(id, brandId);

        return ResponseEntity.ok(brands);
    }

    @DeleteMapping("/perfumers/{id}/brands/{brandId}")
    public ResponseEntity<Void> deleteBrandRelation(@PathVariable long id, @PathVariable long brandId) {
        perfumerBrandService.deleteBrand(id, brandId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/perfumers/{id}/brands")
    public ResponseEntity<Void> deleteAllBrandRelation(@PathVariable long id) {
        perfumerBrandService.deleteAll(id);

        return ResponseEntity
                .noContent()
                .build();
    }


    /////////////////////////// COUNTRY INVERSE RELATIONSHIP ///////////////////////////
    @GetMapping("/perfumers/{id}/country")
    public ResponseEntity<Country> getPerfumerCountry(@PathVariable long id) {
        Country country = perfumerCountryService.getCountry(id);

        return ResponseEntity.ok(country);
    }

    @PutMapping("/perfumers/{id}/country/{countryId}")
    public ResponseEntity<Country> updatePerfumerCountry(@PathVariable long id, @PathVariable long countryId) {
        Country country = perfumerCountryService.updateCountry(id, countryId);

        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/perfumers/{id}/country")
    public ResponseEntity<Void> deleteCountryRelation(@PathVariable long id) {
        perfumerCountryService.deleteCountry(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    /////////////////////////// COMPANY INVERSE RELATIONSHIP ///////////////////////////
    @GetMapping("/perfumers/{id}/company")
    public ResponseEntity<Company> getPerfumerCompany(@PathVariable long id) {
        Company company = perfumerCompanyService.getCompany(id);

        return ResponseEntity.ok(company);
    }

    @PutMapping("/perfumers/{id}/company/{companyId}")
    public ResponseEntity<Company> updatePerfumerCompany(@PathVariable long id, @PathVariable long companyId) {
        Company company = perfumerCompanyService.updateCompany(id, companyId);

        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/perfumers/{id}/company")
    public ResponseEntity<Void> deleteCompanyRelation(@PathVariable long id) {
        perfumerCompanyService.deleteCompany(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
