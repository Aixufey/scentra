package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FragranceServiceV2Impl implements FragranceServiceV2 {
    private final FragranceRepository repo;
    private final BrandServiceImpl brandService;
    private final CountryServiceImpl countryService;
    private final PerfumerServiceImpl perfumerService;
    private final ConcentrationServiceImpl concentrationService;
    private final NoteServiceImpl noteService;
    private final ModelMapper modelMapper;

    @Override
    public List<Fragrance> getAll() {
        return repo.findAll();
    }

    @Override
    public Fragrance getById(long id) {
        return repo.findById(id).orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
    }

    @Override
    public List<Fragrance> getByName(String name) {
        return repo.findFragrancesByNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional
    public FragranceDTO create(FragranceDTO fragrance) {
        // Convert DTO to Entity
        Fragrance fragranceEntity = modelMapper.map(fragrance, Fragrance.class);
        Fragrance savedFragrance = repo.saveAndFlush(fragranceEntity);

        return new FragranceDTO(savedFragrance.getId(), savedFragrance.getName(), savedFragrance.getYear());
    }

    @Override
    @Transactional
    public FragranceDTO update(long id, FragranceDTO fragrance) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.name());
        foundFragrance.setYear(fragrance.year());
        Fragrance updatedFragrance = repo.saveAndFlush(foundFragrance);

        return new FragranceDTO(updatedFragrance.getId(), updatedFragrance.getName(), updatedFragrance.getYear());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public Fragrance updateBrandRelation(long fragranceId, long brandId) {
        Brand foundBrand = brandService.getById(brandId);
        Fragrance fragrance = getById(fragranceId);

        fragrance.setBrand(foundBrand);

        return repo.saveAndFlush(fragrance);
    }

    @Override
    @Transactional
    public void deleteBrandRelation(long fragranceId, long brandId) {
        Fragrance fragrance = getById(fragranceId);

        fragrance.setBrand(null);

        repo.saveAndFlush(fragrance);
    }

    @Override
    @Transactional
    public Fragrance updateCountryRelation(long fragranceId, long countryId) {
        Country foundCountry = countryService.getById(countryId);
        Fragrance fragrance = getById(fragranceId);

        fragrance.setCountry(foundCountry);

        return repo.saveAndFlush(fragrance);
    }

    @Override
    @Transactional
    public void deleteCountryRelation(long fragranceId, long countryId) {
        Fragrance fragrance = getById(fragranceId);

        fragrance.setCountry(null);

        repo.saveAndFlush(fragrance);
    }

    @Override
    public List<Perfumer> getPerfumersRelation(long fragranceId) {
        Fragrance fragrance = getById(fragranceId);

        return List.copyOf(fragrance.getPerfumers());
    }

    @Override
    @Transactional
    public List<Perfumer> updatePerfumerRelation(long fragranceId, long perfumerId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Perfumer> existingPerfumers = fragrance.getPerfumers();

        Perfumer foundPerfumer = perfumerService.getById(perfumerId);
        existingPerfumers.add(foundPerfumer);
        fragrance.setPerfumers(existingPerfumers);

        repo.saveAndFlush(fragrance);

        return List.copyOf(fragrance.getPerfumers());
    }

    @Override
    @Transactional
    public void deletePerfumerRelation(long fragranceId, long perfumerId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Perfumer> existingPerfumers = fragrance.getPerfumers();

        Perfumer perfumer = perfumerService.getById(perfumerId);
        existingPerfumers.remove(perfumer);
        fragrance.setPerfumers(existingPerfumers);

        repo.saveAndFlush(fragrance);
    }

    @Override
    public List<Concentration> getConcentrationsRelation(long fragranceId) {
        Fragrance fragrance = getById(fragranceId);

        return List.copyOf(fragrance.getConcentrations());
    }

    @Override
    @Transactional
    public List<Concentration> updateConcentrationRelation(long fragranceId, long concentrationId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Concentration> existingConcentrations = fragrance.getConcentrations();

        Concentration concentration = concentrationService.getById(concentrationId);
        existingConcentrations.add(concentration);
        fragrance.setConcentrations(existingConcentrations);

        repo.saveAndFlush(fragrance);

        return List.copyOf(fragrance.getConcentrations());
    }

    @Override
    @Transactional
    public void deleteConcentrationRelation(long fragranceId, long concentrationId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Concentration> existingConcentrations = fragrance.getConcentrations();

        Concentration concentration = concentrationService.getById(concentrationId);
        existingConcentrations.remove(concentration);
        fragrance.setConcentrations(existingConcentrations);

        repo.saveAndFlush(fragrance);
    }

    @Override
    public List<Note> getNotesRelation(long fragranceId) {
        Fragrance fragrance = getById(fragranceId);

        return List.copyOf(fragrance.getNotes());
    }

    @Override
    @Transactional
    public List<Note> updateNoteRelation(long fragranceId, long noteId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Note> existingNotes = fragrance.getNotes();

        Note note = noteService.getById(noteId);
        existingNotes.add(note);
        fragrance.setNotes(existingNotes);

        repo.saveAndFlush(fragrance);

        return List.copyOf(fragrance.getNotes());
    }

    @Override
    @Transactional
    public void deleteNoteRelation(long fragranceId, long noteId) {
        Fragrance fragrance = getById(fragranceId);
        Set<Note> existingNotes = fragrance.getNotes();

        Note note = noteService.getById(noteId);
        existingNotes.remove(note);
        fragrance.setNotes(existingNotes);

        repo.saveAndFlush(fragrance);
    }
}
