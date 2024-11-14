package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.repository.CountryRepository;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class FragranceCountryServiceImpl implements FragranceCountryService {
    private final FragranceRepository fragranceRepository;
    private final CountryRepository countryRepository;

    @Override
    public Country getCountry(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getCountry() == null) {
            throw new CountryNotFoundException("Country not found for fragrance with id " + fragranceId);
        }

        return fragrance.getCountry();
    }

    @Override
    @Transactional
    public Country updateCountry(long fragranceId, long countryId) {
        Fragrance fragrance = getFragrance(fragranceId);

        Country country = getFragranceCountry(countryId);

        fragrance.setCountry(country);

        Set<Fragrance> fragrances = country.getFragrances();
        fragrances.add(fragrance);
        country.setFragrances(fragrances);

        fragranceRepository.saveAndFlush(fragrance);
        countryRepository.saveAndFlush(country);

        return country;
    }

    @Override
    @Transactional
    public void deleteCountry(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getCountry() == null) {
            throw new CountryNotFoundException("Fragrance with id " + fragranceId + " has no country");
        }

        fragrance.setCountry(null);

        fragranceRepository.saveAndFlush(fragrance);
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + fragranceId + " not found"));
    }

    private Country getFragranceCountry(long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException("Country with id " + countryId + " not found"));
    }
}
