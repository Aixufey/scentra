package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.service.FragranceServiceV2Impl;
import com.rinseo.scentra.service.fragrance.*;
import com.rinseo.scentra.utils.EntityTransformer;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class FragranceController {
    private final FragranceServiceV2Impl service;
    private final FragrancePerfumerServiceImpl fragrancePerfumerService;
    private final FragranceBrandServiceImpl fragranceBrandService;
    private final FragranceCountryServiceImpl fragranceCountryService;
    private final FragranceNoteServiceImpl fragranceNoteService;
    private final FragranceConcentrationServiceImpl fragranceConcentrationService;
    private final EntityTransformer entityTransformer;

    @Autowired
    public FragranceController(
            FragranceServiceV2Impl service,
            FragrancePerfumerServiceImpl fragrancePerfumerService,
            FragranceBrandServiceImpl fragranceBrandService,
            FragranceCountryServiceImpl fragranceCountryService,
            FragranceNoteServiceImpl fragranceNoteService,
            FragranceConcentrationServiceImpl fragranceConcentrationService,
            EntityTransformer entityTransformer) {
        this.service = service;
        this.fragrancePerfumerService = fragrancePerfumerService;
        this.fragranceBrandService = fragranceBrandService;
        this.fragranceCountryService = fragranceCountryService;
        this.fragranceNoteService = fragranceNoteService;
        this.fragranceConcentrationService = fragranceConcentrationService;
        this.entityTransformer = entityTransformer;
    }

    @GetMapping("/v1/fragrances")
    public ResponseEntity<List<Fragrance>> getAllFragrances() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/v1/fragrances/{id}")
    public ResponseEntity<Fragrance> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/v1/fragrances/query")
    public ResponseEntity<List<Fragrance>> getByName(@RequestParam(required = false) String name) {
        if (name != null) {
            String sanitizedName = name.replaceAll("[^a-zA-Z0-9 ]", "");
            log.info("Sanitized name: {}", sanitizedName);
            return ResponseEntity.ok(service.getByName(sanitizedName));
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }

    /**
     * Posting does not work with H2 because of returning the generated id is not supported.
     */
    @PostMapping("/v1/fragrances")
    public ResponseEntity<Fragrance> save(
            @Valid @ModelAttribute FragranceDTO fragrance,
            @Nullable @RequestPart(value = "file") MultipartFile file) {
        Fragrance fragranceDTO = service.create(fragrance, file);
        Fragrance mapped = entityTransformer.mapEntityUrl(fragranceDTO, Fragrance.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mapped.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(mapped);
    }

    @PutMapping("/v1/fragrances/{id}")
    public ResponseEntity<Fragrance> update(
            @PathVariable long id,
            @Valid @ModelAttribute FragranceDTO fragrance,
            @Nullable @RequestPart(value = "file") MultipartFile file) {
        Fragrance fragranceDTO = service.update(id, fragrance, file);

        return ResponseEntity.ok(fragranceDTO);
    }

    @DeleteMapping("/v1/fragrances/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    ///////////////// BRAND INVERSE RELATIONSHIP /////////////////
    @GetMapping("/v1/fragrances/{fragranceId}/brand")
    public ResponseEntity<Brand> getBrandRelation(@PathVariable long fragranceId) {
        Brand brand = fragranceBrandService.getBrand(fragranceId);

        return ResponseEntity.ok(brand);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/brand/{brandId}")
    public ResponseEntity<Brand> updateBrandRelation(@PathVariable long fragranceId, @PathVariable long brandId) {
        Brand brand = fragranceBrandService.updateBrand(fragranceId, brandId);

        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/brand")
    public ResponseEntity<Void> deleteBrandRelation(@PathVariable long fragranceId) {
        fragranceBrandService.deleteBrand(fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }

    ////////////////// COUNTRY INVERSE RELATIONSHIP /////////////////
    @GetMapping("/v1/fragrances/{fragranceId}/country")
    public ResponseEntity<Country> getCountryRelation(@PathVariable long fragranceId) {
        Country country = fragranceCountryService.getCountry(fragranceId);

        return ResponseEntity.ok(country);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/country/{countryId}")
    public ResponseEntity<Country> updateCountryRelation(@PathVariable long fragranceId, @PathVariable long countryId) {
        Country country = fragranceCountryService.updateCountry(fragranceId, countryId);

        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/country")
    public ResponseEntity<Void> deleteCountryRelation(@PathVariable long fragranceId) {
        fragranceCountryService.deleteCountry(fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }

    ///////////////// PERFUMERS INVERSE RELATIONSHIP /////////////////
    @GetMapping("/v1/fragrances/{fragranceId}/perfumers")
    public ResponseEntity<List<Perfumer>> getPerfumers(@PathVariable long fragranceId) {
        List<Perfumer> perfumers = fragrancePerfumerService.getAll(fragranceId);

        return ResponseEntity.ok(perfumers);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/perfumers")
    public ResponseEntity<List<Perfumer>> updatePerfumers(@PathVariable long fragranceId, @RequestBody List<Long> perfumerIds) {
        List<Perfumer> perfumers = fragrancePerfumerService.updatePerfumers(fragranceId, perfumerIds);

        return ResponseEntity.ok(perfumers);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/perfumers/{perfumerId}")
    public ResponseEntity<Void> deletePerfumer(@PathVariable long fragranceId, @PathVariable long perfumerId) {
        fragrancePerfumerService.deletePerfumer(fragranceId, perfumerId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/perfumers")
    public ResponseEntity<Void> deleteAllPerfumers(@PathVariable long fragranceId) {
        fragrancePerfumerService.deleteAll(fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }

    ////////////////// CONCENTRATIONS INVERSE RELATIONSHIP /////////////////
    @GetMapping("/v1/fragrances/{fragranceId}/concentrations")
    public ResponseEntity<List<Concentration>> getConcentrations(@PathVariable long fragranceId) {
        List<Concentration> concentrations = fragranceConcentrationService.getAll(fragranceId);

        return ResponseEntity.ok(concentrations);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/concentrations/{concentrationId}")
    public ResponseEntity<Concentration> updateConcentrations(@PathVariable long fragranceId, @PathVariable long concentrationId) {
        Concentration concentrations = fragranceConcentrationService.updateConcentration(fragranceId, concentrationId);

        return ResponseEntity.ok(concentrations);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/concentrations/{concentrationId}")
    public ResponseEntity<Void> deleteConcentration(@PathVariable long fragranceId, @PathVariable long concentrationId) {
        fragranceConcentrationService.deleteConcentration(fragranceId, concentrationId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/concentrations")
    public ResponseEntity<Void> deleteAllConcentrations(@PathVariable long fragranceId) {
        fragranceConcentrationService.deleteAll(fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }

    ////////////////// NOTES INVERSE RELATIONSHIP /////////////////
    @GetMapping("/v1/fragrances/{fragranceId}/notes")
    public ResponseEntity<List<Note>> getNotes(@PathVariable long fragranceId) {
        List<Note> notes = fragranceNoteService.getAll(fragranceId);

        return ResponseEntity.ok(notes);
    }

    @PatchMapping("/v1/fragrances/{fragranceId}/notes")
    public ResponseEntity<List<Note>> updateNotes(@PathVariable long fragranceId, @RequestBody List<Long> noteIds) {
        List<Note> notes = fragranceNoteService.updateNotes(fragranceId, noteIds);

        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long fragranceId, @PathVariable long noteId) {
        fragranceNoteService.deleteNote(fragranceId, noteId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/v1/fragrances/{fragranceId}/notes")
    public ResponseEntity<Void> deleteAllNotes(@PathVariable long fragranceId) {
        fragranceNoteService.deleteAll(fragranceId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
