package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    List<Country> getAll();

    Country getByName(String name);

    Country getById(long id);

    CountryDTO update(long id, CountryDTO country);

    CountryDTO create(CountryDTO country);

    void delete(long id);
}
