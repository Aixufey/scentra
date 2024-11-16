package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.repository.CountryRepository;
import com.rinseo.scentra.repository.PerfumerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PerfumerCountryServiceImpl implements PerfumerCountryService {
    private final PerfumerRepository repo;
    private final CountryRepository countryRepository;

    @Override
    public Country getCountry(long perfumerId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer with id " + perfumerId + " not found"));

        if (perfumer.getCountry() == null) {
            throw new PerfumerNotFoundException("Perfumer with id " + perfumerId + " does not have a country");
        }

        return perfumer.getCountry();
    }

    @Override
    @Transactional
    public Country updateCountry(long perfumerId, long countryId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer with id " + perfumerId + " not found"));

        Country country = getPerfumerCountry(countryId);

        perfumer.setCountry(country);
        repo.saveAndFlush(perfumer);

        return country;
    }

    @Override
    @Transactional
    public void deleteCountry(long perfumerId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer with id " + perfumerId + " not found"));

        perfumer.setCountry(null);

        repo.saveAndFlush(perfumer);
    }

    public Country getPerfumerCountry(long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException("Country with id " + countryId + " not found"));
    }
}
