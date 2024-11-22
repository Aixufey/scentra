package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import com.rinseo.scentra.service.fragrance.*;
import com.rinseo.scentra.utils.EntityTransformer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudinaryServiceImpl cloudinaryService;
    private final EntityTransformer entityTransformer;

    @Override
    public List<Fragrance> getAll() {
        List<Fragrance> fragrances = repo.findAll();
        return fragrances.stream()
                .map(f -> entityTransformer
                        .mapEntityUrl(f, Fragrance.class))
                .collect(Collectors.toList());
    }

    @Override
    public Fragrance getById(long id) {
        Fragrance fragrance = repo.findById(id).orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        return entityTransformer.mapEntityUrl(fragrance, Fragrance.class);
    }

    @Override
    public List<Fragrance> getByName(String name) {
        List<Fragrance> fragrances = repo.findFragrancesByNameContainsIgnoreCase(name);
        return fragrances.stream()
                .map(f -> entityTransformer
                        .mapEntityUrl(f, Fragrance.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Fragrance create(FragranceDTO fragrance, MultipartFile file) {
        // Convert DTO to Entity
        Fragrance fragranceEntity = modelMapper.map(fragrance, Fragrance.class);
        Fragrance byName = repo.findByName(fragrance.getName());

        if (byName != null) {
            throw new UniqueViolationException("Fragrance with name: " + fragrance.getName() + " already exists");
        }

        // Set default image if not provided
//        if (fragranceEntity.getImageUrl() == null || fragranceEntity.getImageUrl().isBlank()) {
//            fragranceEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731762217/scentra/fragrance/fragrance_iswvjk.png");
//        }
        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/fragrance");
            fragranceEntity.setImageUrl(publicId);
        } else {
            fragranceEntity.setImageUrl("fragrance_iswvjk.png");
        }

        updateMetadata(fragranceEntity, fragrance);

        return repo.saveAndFlush(fragranceEntity);
    }

    /**
     * Update entity if the DTO contains the metadata.
     *
     * @param entity        The entity to update
     * @param fragranceData The DTO containing the metadata
     */
    private void updateMetadata(Fragrance entity, FragranceDTO fragranceData) {
        if (fragranceData.getBrandId() != null) {
            Brand brand = brandService.getFragranceBrand(fragranceData.getBrandId());
            entity.setBrand(brand);
        }
        if (fragranceData.getCountryId() != null) {
            Country country = countryService.getFragranceCountry(fragranceData.getCountryId());
            entity.setCountry(country);
        }
        if (fragranceData.getPerfumerIds() != null) {
            Set<Perfumer> matching = getPerfumers(fragranceData);
            entity.setPerfumers(matching);
        }
        if (fragranceData.getNoteIds() != null) {
            Set<Note> matching = getNotes(fragranceData);
            entity.setNotes(matching);
        }
        if (fragranceData.getConcentrationIds() != null) {
            Set<Concentration> matching = getConcentrations(fragranceData);
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
        return getEntities(noteService::getNote, fragrance.getNoteIds());
    }

    private Set<Concentration> getConcentrations(FragranceDTO fragrance) {
        return getEntities(concentrationService::getConcentration, fragrance.getConcentrationIds());
    }

    private Set<Perfumer> getPerfumers(FragranceDTO fragrance) {
        return getEntities(perfumerService::getPerfumer, fragrance.getPerfumerIds());
    }

    @Override
    @Transactional
    public Fragrance update(long id, FragranceDTO fragrance, MultipartFile file) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.getName());
        foundFragrance.setYear(fragrance.getYear());

//        if (fragrance.imageUrl() != null && !fragrance.imageUrl().isBlank()) {
//            foundFragrance.setImageUrl(fragrance.imageUrl());
//        }
        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/fragrance");
            foundFragrance.setImageUrl(publicId);
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
