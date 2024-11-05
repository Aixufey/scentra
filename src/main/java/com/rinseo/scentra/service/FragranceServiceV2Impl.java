package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public Fragrance create(FragranceDTO fragrance) {
        // Convert DTO to Entity
        Fragrance fragranceEntity = modelMapper.map(fragrance, Fragrance.class);
        Fragrance byName = repo.findByName(fragrance.name());

        if (byName != null) {
            throw new UniqueViolationException("Fragrance with name: " + fragrance.name() + " already exists");
        }

        updateMetadata(fragranceEntity, fragrance);

        return repo.saveAndFlush(fragranceEntity);
    }

    /**
     * Update entity if the DTO contains the metadata.
     *
     * @param entity       The entity to update
     * @param fragranceDTO The DTO containing the metadata
     */
    private void updateMetadata(Fragrance entity, FragranceDTO fragranceDTO) {
        if (fragranceDTO.brandId() != null) {
            Brand brand = brandService.getById(fragranceDTO.brandId());
            entity.setBrand(brand);
        }
        if (fragranceDTO.countryId() != null) {
            Country country = countryService.getById(fragranceDTO.countryId());
            entity.setCountry(country);
        }
        if (fragranceDTO.perfumerIds() != null) {
            Set<Perfumer> matching = getPerfumers(fragranceDTO);
            entity.setPerfumers(matching);
        }
        if (fragranceDTO.noteIds() != null) {
            Set<Note> matching = getNotes(fragranceDTO);
            entity.setNotes(matching);
        }
        if (fragranceDTO.concentrationIds() != null) {
            Set<Concentration> matching = getConcentrations(fragranceDTO);
            entity.setConcentrations(matching);
        }
    }

    /**
     * Generic method to get entities by their ids from a service then filter out the nulls.
     *
     * @param service The service to use to get the entity
     * @param ids     Set of ids
     * @param <T>     The type of entity
     * @return Set of entities
     */
    private <T> Set<T> getEntities(Function<Long, T> service, Set<Long> ids) {
        /*
        * A refactored version of the above
        * Set<Long> ids = entity.getIds();
        Set<Perfumer> matching = xyzService
                .getAll()
                .stream()
                .filter(e -> ids.contains(e.getId()))
                .collect(Collectors.toSet());
        return matching;
        * */
        return ids.stream()
                .map(service)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<Note> getNotes(FragranceDTO fragrance) {
        return getEntities(noteService::getById, fragrance.noteIds());
    }

    private Set<Concentration> getConcentrations(FragranceDTO fragrance) {
        return getEntities(concentrationService::getById, fragrance.concentrationIds());
    }

    private Set<Perfumer> getPerfumers(FragranceDTO fragrance) {
        return getEntities(perfumerService::getById, fragrance.perfumerIds());
    }

    @Override
    @Transactional
    public Fragrance update(long id, FragranceDTO fragrance) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.name());
        foundFragrance.setYear(fragrance.year());

        updateMetadata(foundFragrance, fragrance);

        return repo.saveAndFlush(foundFragrance);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BrandRelationService
    ///////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////
    // CountryRelationService
    ///////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////
    // PerfumerRelationService
    ///////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////
    // ConcentrationRelationService
    ///////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////
    // NoteRelationService
    ///////////////////////////////////////////////////////////////////////////

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
