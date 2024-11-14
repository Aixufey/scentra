package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.exception.ConcentrationNotFoundException;
import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.repository.ConcentrationRepository;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FragranceConcentrationServiceImpl implements FragranceConcentrationService {
    private final FragranceRepository fragranceRepository;
    private final ConcentrationRepository concentrationRepository;

    @Override
    public List<Concentration> getAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getConcentrations() == null) {
            throw new ConcentrationNotFoundException("Fragment with id " + fragranceId + " has no concentrations");
        }

        return List.copyOf(fragrance.getConcentrations());
    }

    @Override
    @Transactional
    public Concentration updateConcentration(long fragranceId, long concentrationId) {
        // Find fragrance
        Fragrance fragrance = getFragrance(fragranceId);

        // Find concentration
        Concentration concentration = getConcentration(concentrationId);
        // Get existing concentrations
        Set<Concentration> concentrations = fragrance.getConcentrations();
        // Add to existing concentrations
        concentrations.add(concentration);
        // Set new concentrations
        fragrance.setConcentrations(concentrations);

        // Inverse side: Get existing fragrances for concentration
        Set<Fragrance> fragrances = concentration.getFragrances();
        // Add to existing fragrances
        fragrances.add(fragrance);
        // Set new fragrances
        concentration.setFragrances(fragrances);

        fragranceRepository.saveAndFlush(fragrance);
        concentrationRepository.saveAndFlush(concentration);

        return concentration;
    }

    @Override
    @Transactional
    public void deleteConcentration(long fragranceId, long concentrationId) {
        Fragrance fragrance = getFragrance(fragranceId);

        Concentration concentration = getConcentration(concentrationId);

        // Remove concentration from fragrance
        Set<Concentration> concentrations = fragrance.getConcentrations();
        concentrations.remove(concentration);
        fragrance.setConcentrations(concentrations);

        // Inverse side: Remove fragrance from concentration
        Set<Fragrance> fragrances = concentration.getFragrances();
        fragrances.remove(fragrance);
        concentration.setFragrances(fragrances);

        fragranceRepository.saveAndFlush(fragrance);
        concentrationRepository.saveAndFlush(concentration);
    }

    @Override
    public void deleteAll(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        // Inverse side: Find all concentrations for fragrance
        // Remove fragrance from concentrations
        Set<Concentration> concentrations = fragrance.getConcentrations();
        for (var concentration : concentrations) {
            Set<Fragrance> fragrances = concentration.getFragrances();
            fragrances.remove(fragrance);
        }
        // Clear fragrance concentrations
        fragrance.getConcentrations().clear();

        concentrationRepository.saveAllAndFlush(concentrations);
        fragranceRepository.saveAndFlush(fragrance);
    }

    private Concentration getConcentration(long concentrationId) {
        return concentrationRepository.findById(concentrationId)
                .orElseThrow(() -> new ConcentrationNotFoundException("Concentration with id " + concentrationId + " not found"));
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + fragranceId + " not found"));
    }
}
