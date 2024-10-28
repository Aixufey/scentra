package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import com.rinseo.scentra.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repo;

    @Override
    public List<Country> getAll() {
        return repo.findAll();
    }

    @Override
    public Country getByName(String name) {
        return repo.findCountryByNameEqualsIgnoreCase(name);
    }

    @Override
    public Country getById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
    }

    @Override
    public CountryDTO update(long id, CountryDTO country) {
        Country foundCountry = repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
        foundCountry.setName(country.name());
        Country updatedCountry = repo.saveAndFlush(foundCountry);

        return new CountryDTO(updatedCountry.getId(), updatedCountry.getName());
    }

    @Override
    public CountryDTO create(CountryDTO country) {
        Country countryEntity = new ModelMapper().map(country, Country.class);
        Country savedCountry = repo.saveAndFlush(countryEntity);

        return new CountryDTO(savedCountry.getId(), savedCountry.getName());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
