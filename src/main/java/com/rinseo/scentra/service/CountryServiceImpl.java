package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.CountryDTO;
import com.rinseo.scentra.repository.CountryRepository;
import com.rinseo.scentra.utils.EntityTransformer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repo;
    private final ModelMapper modelMapper;
    private final EntityTransformer entityTransformer;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public List<Country> getAll() {
        List<Country> countries = repo.findAll();
        return countries.stream()
                .map(country -> entityTransformer.mapEntityUrl(country, Country.class))
                .collect(Collectors.toList());
    }

    @Override
    public Country getByName(String name) {
        Country country = repo.findCountryByNameEqualsIgnoreCase(name);
        return entityTransformer.mapEntityUrl(country, Country.class);
    }

    @Override
    public Country getById(long id) {
        Country country = repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
        return entityTransformer.mapEntityUrl(country, Country.class);
    }

    @Override
    @Transactional
    public Country update(long id, CountryDTO country, MultipartFile file) {
        Country foundCountry = repo.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));
        foundCountry.setName(country.getName());

        if (file != null) {
            //String publicId = cloudinaryService.uploadImageFile(file, "scentra/country");
            //foundCountry.setImageUrl(publicId);
        }

        Country saved = repo.saveAndFlush(foundCountry);
        return entityTransformer.mapEntityUrl(saved, Country.class);
    }

    @Override
    @Transactional
    public Country create(CountryDTO country, MultipartFile file) {
        Country countryEntity = modelMapper.map(country, Country.class);

        if (repo.findCountryByNameEqualsIgnoreCase(country.getName()) != null) {
            throw new UniqueViolationException("Country with name " + country.getName() + " already exists");
        }

        if (file != null) {
            //String publicId = cloudinaryService.uploadImageFile(file, "scentra/country");
            //countryEntity.setImageUrl(publicId);
        } else {
            //countryEntity.setImageUrl("country_nipdod.png");
        }

        Country saved = repo.saveAndFlush(countryEntity);
        return entityTransformer.mapEntityUrl(saved, Country.class);
    }

    @Override
    @Transactional
    public void delete(long id) {
        repo.deleteById(id);
    }
}
