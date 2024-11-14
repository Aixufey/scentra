package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.repository.FragranceRepository;
import com.rinseo.scentra.repository.PerfumerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FragrancePerfumerServiceImpl implements FragrancePerfumerService {
    private final FragranceRepository fragranceRepository;
    private final PerfumerRepository perfumerRepository;

    @Override
    public List<Perfumer> getAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        return List.copyOf(fragrance.getPerfumers());
    }

    @Override
    @Transactional
    public List<Perfumer> updatePerfumers(long fragranceId, List<Long> perfumerIds) {
        // Find the fragrance
        Fragrance fragrance = getFragrance(fragranceId);

        // Validate perfumer IDs
        for (Long id : perfumerIds) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid perfumer ID: " + id);
            }
        }

        // Find all perfumers by IDs
        List<Perfumer> foundPerfumers = perfumerRepository.findAllById(perfumerIds);

        // Owner side: Get existing perfumers and add new perfumers
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.addAll(foundPerfumers);
        fragrance.setPerfumers(perfumers);

        // Inverse side: For each perfumer add the fragrance in their creations list
        for (var perfumer : foundPerfumers) {
            Set<Fragrance> fragrances = perfumer.getFragrances();
            fragrances.add(fragrance);
            perfumer.setFragrances(fragrances);
        }

        fragranceRepository.saveAndFlush(fragrance);
        perfumerRepository.saveAllAndFlush(foundPerfumers);

        return List.copyOf(fragrance.getPerfumers());
    }

    @Override
    @Transactional
    public void deletePerfumer(long fragranceId, long perfumerId) {
        // Find the fragrance
        Fragrance fragrance = getFragrance(fragranceId);
        // Find the perfumer
        Perfumer perfumer = getPerfumer(perfumerId);
        // Validate if the perfumer is associated with the fragrance
        if (!fragrance.getPerfumers().contains(perfumer)) {
            throw new IllegalArgumentException("Perfumer with id " + perfumerId + " is not associated with fragrance with id " + fragranceId);
        }

        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.remove(perfumer);
        fragrance.setPerfumers(perfumers);

        Set<Fragrance> fragrances = perfumer.getFragrances();
        fragrances.remove(fragrance);
        perfumer.setFragrances(fragrances);

        perfumerRepository.saveAndFlush(perfumer);
        fragranceRepository.saveAndFlush(fragrance);
    }

    @Override
    @Transactional
    public void deleteAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);
        // Inverse side: Get all perfumers and remove the fragrance from their creation list
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        for (var perfumer : perfumers) {
            Set<Fragrance> fragrances = perfumer.getFragrances();
            fragrances.remove(fragrance);
        }
        // Owner side: Clear all perfumers
        fragrance.getPerfumers().clear();

        fragranceRepository.saveAndFlush(fragrance);
        perfumerRepository.saveAllAndFlush(perfumers);
    }

    private Perfumer getPerfumer(long perfumerId) {
        return perfumerRepository.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer with id " + perfumerId + " not found"));
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + fragranceId + " not found"));
    }
}
