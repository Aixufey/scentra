package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import com.rinseo.scentra.service.fragrance.*;
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
    private final FragranceBrandServiceImpl brandService;
    private final FragranceCountryServiceImpl countryService;
    private final FragrancePerfumerServiceImpl perfumerService;
    private final FragranceConcentrationServiceImpl concentrationService;
    private final FragranceNoteServiceImpl noteService;
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

        // Set default image if not provided
        if (fragranceEntity.getImageUrl() == null || fragranceEntity.getImageUrl().isBlank()) {
            fragranceEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731762217/scentra/fragrance/fragrance_iswvjk.png");
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
            Brand brand = brandService.getFragranceBrand(fragranceDTO.brandId());
            entity.setBrand(brand);
        }
        if (fragranceDTO.countryId() != null) {
            Country country = countryService.getFragranceCountry(fragranceDTO.countryId());
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
        return getEntities(noteService::getNote, fragrance.noteIds());
    }

    private Set<Concentration> getConcentrations(FragranceDTO fragrance) {
        return getEntities(concentrationService::getConcentration, fragrance.concentrationIds());
    }

    private Set<Perfumer> getPerfumers(FragranceDTO fragrance) {
        return getEntities(perfumerService::getPerfumer, fragrance.perfumerIds());
    }

    @Override
    @Transactional
    public Fragrance update(long id, FragranceDTO fragrance) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.name());
        foundFragrance.setYear(fragrance.year());

        if (fragrance.imageUrl() != null && !fragrance.imageUrl().isBlank()) {
            foundFragrance.setImageUrl(fragrance.imageUrl());
        }

        updateMetadata(foundFragrance, fragrance);

        return repo.saveAndFlush(foundFragrance);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        brandService.deleteBrand(id);
        countryService.deleteCountry(id);
        perfumerService.deleteAll(id);
        concentrationService.deleteAll(id);
        noteService.deleteAll(id);

        repo.deleteById(id);
    }

}
