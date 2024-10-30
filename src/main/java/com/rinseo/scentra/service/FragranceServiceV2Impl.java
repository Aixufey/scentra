package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FragranceServiceV2Impl implements FragranceServiceV2 {
    private final FragranceRepository repo;
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
    public FragranceDTO create(FragranceDTO fragrance) {
        // Convert DTO to Entity
        Fragrance fragranceEntity = modelMapper.map(fragrance, Fragrance.class);
        Fragrance savedFragrance = repo.saveAndFlush(fragranceEntity);

        return new FragranceDTO(savedFragrance.getId(), savedFragrance.getName(), savedFragrance.getYear());
    }

    @Override
    public FragranceDTO update(long id, FragranceDTO fragrance) {
        Fragrance foundFragrance = repo.findById(id)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + id));
        foundFragrance.setName(fragrance.name());
        foundFragrance.setYear(fragrance.year());
        Fragrance updatedFragrance = repo.saveAndFlush(foundFragrance);

        return new FragranceDTO(updatedFragrance.getId(), updatedFragrance.getName(), updatedFragrance.getYear());
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }
}
