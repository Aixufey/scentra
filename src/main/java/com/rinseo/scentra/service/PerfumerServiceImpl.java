package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.repository.FragranceRepository;
import com.rinseo.scentra.repository.PerfumerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PerfumerServiceImpl implements PerfumerService {
    private final PerfumerRepository repo;
    private final FragranceRepository fragranceRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Perfumer> getAll() {
        return repo.findAll();
    }

    @Override
    public Perfumer getById(long id) {
        return repo.findById(id).orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
    }

    @Override
    public List<Perfumer> getByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public PerfumerDTO create(PerfumerDTO perfumer) {
        Perfumer perfumerEntity = modelMapper.map(perfumer, Perfumer.class);
        Perfumer savedPerfumer = repo.saveAndFlush(perfumerEntity);

        return new PerfumerDTO(savedPerfumer.getId(), savedPerfumer.getName());
    }

    @Override
    public PerfumerDTO update(long id, PerfumerDTO perfumer) {
        Perfumer foundPerfumer = repo.findById(id)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        foundPerfumer.setName(perfumer.name());
        Perfumer updatedPerfumer = repo.saveAndFlush(foundPerfumer);

        return new PerfumerDTO(updatedPerfumer.getId(), updatedPerfumer.getName());
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Fragrance> getFragrancesRelation(long id) {
        Perfumer perfumer = getById(id);

        return List.copyOf(perfumer.getFragrances());
    }

    @Override
    @Transactional
    public List<Fragrance> updateFragranceRelation(long id, long fragranceId) {
        // Find perfumer
        Perfumer perfumer = getById(id);
        // Find fragrance
        Fragrance fragrance = fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new PerfumerNotFoundException("Fragrance not found with id: " + fragranceId));
        // Inverse side: Add found fragrance to perfumer's collection
        Set<Fragrance> fragrances = perfumer.getFragrances();
        fragrances.add(fragrance);
        perfumer.setFragrances(fragrances);
        // Owner side: Add perfumer to fragrance's collection
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.add(perfumer);
        fragrance.setPerfumers(perfumers);

        // Save changes on both sides to persist the relationship
        repo.saveAndFlush(perfumer);
        fragranceRepository.saveAndFlush(fragrance);

        return List.copyOf(fragrances);
    }

    @Override
    @Transactional
    public void deleteFragranceRelation(long id, long fragranceId) {
        // Cascade strategy in JPA is typically done on the owning side of the relationship.
        // It will not propagate changes to the inverse side.
        // Therefore, we need to modify changes on both sides to persist the relationship.
        // Find perfumer
        Perfumer perfumer = getById(id);
        // Find fragrance
        Fragrance fragrance = fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new PerfumerNotFoundException("Fragrance not found with id: " + fragranceId));
        // Inverse side: Remove found fragrance from perfumer's collection
        Set<Fragrance> fragrances = perfumer.getFragrances();
        fragrances.remove(fragrance);
        perfumer.setFragrances(fragrances);
        // Owner side: Remove perfumer from fragrance's collection
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.remove(perfumer);
        fragrance.setPerfumers(perfumers);

        // Save changes on both sides to persist the relationship
        repo.saveAndFlush(perfumer);
        fragranceRepository.saveAndFlush(fragrance);
    }
}
