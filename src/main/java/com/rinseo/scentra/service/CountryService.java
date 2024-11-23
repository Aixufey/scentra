package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CountryService {
    List<Country> getAll();

    Country getByName(String name);

    Country getById(long id);

    Country update(long id, CountryDTO country, MultipartFile file);

    Country create(CountryDTO country, MultipartFile file);

    void delete(long id);
}
