package com.rinseo.scentra.service.perfumer;

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


/**
 * The `PerfumerFragranceServiceImpl` class provides services to manage the relationship between `Perfumer` and `Fragrance` entities.
 * It implements the `PerfumerFragranceService` interface, which defines methods for retrieving, updating, and deleting fragrances
 * associated with a perfumer.
 * The service ensures that changes are persisted on both sides of the relationship, adhering to the principles of transactional integrity and consistency.
 * By separating these operations into a dedicated interface and implementation,
 * the design maintains a clean, maintainable, and scalable architecture, following SOLID principles.
 */
@Service
@AllArgsConstructor
public class PerfumerFragranceServiceImpl implements PerfumerFragranceService {
    private final PerfumerRepository perfumerRepository;
    private final FragranceRepository fragranceRepository;

    @Override
    public List<Fragrance> getAll(long perfumerId) {
        Perfumer perfumer = getPerfumer(perfumerId);

        return List.copyOf(perfumer.getFragrances());
    }

    @Override
    @Transactional
    public List<Fragrance> updateFragrance(long perfumerId, long fragranceId) {
        // Find perfumer
        Perfumer perfumer = getPerfumer(perfumerId);
        // Find fragrance
        Fragrance fragrance = getFragrance(fragranceId);
        // Inverse side: Add found fragrance to perfumer's collection
        Set<Fragrance> fragrances = perfumer.getFragrances();
        fragrances.add(fragrance);
        perfumer.setFragrances(fragrances);
        // Owner side: Add perfumer to fragrance's collection
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.add(perfumer);
        fragrance.setPerfumers(perfumers);

        // Save changes on both sides to persist the relationship
        perfumerRepository.saveAndFlush(perfumer);
        fragranceRepository.saveAndFlush(fragrance);

        return List.copyOf(perfumer.getFragrances());
    }

    @Override
    @Transactional
    public void deleteFragrance(long perfumerId, long fragranceId) {
        // Cascade strategy in JPA is typically done on the owning side of the relationship.
        // It will not propagate changes to the inverse side.
        // Therefore, we need to modify changes on both sides to persist the relationship.
        // Find perfumer
        Perfumer perfumer = getPerfumer(perfumerId);
        // Find fragrance
        Fragrance fragrance = getFragrance(fragranceId);
        // Inverse side: Remove found fragrance from perfumer's collection
        Set<Fragrance> fragrances = perfumer.getFragrances();
        fragrances.remove(fragrance);
        perfumer.setFragrances(fragrances);
        // Owner side: Remove perfumer from fragrance's collection
        Set<Perfumer> perfumers = fragrance.getPerfumers();
        perfumers.remove(perfumer);
        fragrance.setPerfumers(perfumers);

        // Save changes on both sides to persist the relationship
        perfumerRepository.saveAndFlush(perfumer);
        fragranceRepository.saveAndFlush(fragrance);
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance not found with id: " + fragranceId));
    }

    private Perfumer getPerfumer(long perfumerId) {
        return perfumerRepository.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + perfumerId));
    }
}
