package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.BrandNotFoundException;
import com.rinseo.scentra.exception.CompanyNotFoundException;
import com.rinseo.scentra.exception.CountryNotFoundException;
import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.Country;
import com.rinseo.scentra.model.dto.BrandDTO;
import com.rinseo.scentra.repository.BrandRepository;
import com.rinseo.scentra.repository.CompanyRepository;
import com.rinseo.scentra.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repo;
    private final CountryRepository countryRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Brand> getAll() {
        return repo.findAll();
    }

    @Override
    public Brand getById(long id) {
        return repo.findById(id).orElseThrow(() ->
                new BrandNotFoundException("Brand with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Brand update(long id, BrandDTO brand) {
        Brand foundBrand = repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand by id " + id + " was not found"));

        // Before updating, check if brand with the same name already exists
        Brand brandExist = repo.findByNameEqualsIgnoreCase(brand.name());
        if (brandExist != null) {
            throw new IllegalArgumentException("Brand with name " + brand.name() + " already exists");
        }
        foundBrand.setName(brand.name());

        if (brand.imageUrl() != null && !brand.imageUrl().isBlank()) {
            foundBrand.setImageUrl(brand.imageUrl());
        }

        updateMetadata(brand, foundBrand);

        return repo.saveAndFlush(foundBrand);
    }

    private void updateMetadata(BrandDTO brandDTO, Brand brandEntity) {
        Country country = countryRepository.findById(brandDTO.countryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with id " + brandDTO.countryId() + " not found"));
        brandEntity.setCountry(country);

        if (brandDTO.companyId() != null) {
            Company company = companyRepository.findById(brandDTO.companyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company with id " + brandDTO.companyId() + " not found"));
            brandEntity.setCompany(company);
        }
    }

    @Override
    public Brand getByName(String name) {
        Brand brand = repo.findByNameEqualsIgnoreCase(name);
        if (brand == null) {
            throw new BrandNotFoundException("Brand with name " + name + " not found");
        }
        return brand;
    }

    @Override
    @Transactional
    public Brand create(BrandDTO brand) {
        Brand brandEntity = modelMapper.map(brand, Brand.class);

        // check if brand already exists
        if (repo.findByNameEqualsIgnoreCase(brand.name()) != null) {
            throw new IllegalArgumentException("Brand with name " + brand.name() + " already exists");
        }

        if (brand.imageUrl() == null || brand.imageUrl().isBlank()) {
            brandEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731784429/scentra/brand_dubl8q_b_rgb_FFFFFF_hvsst6.png");
        }

        updateMetadata(brand, brandEntity);

        return repo.saveAndFlush(brandEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + id + " not found"));

        repo.deleteById(id);
    }
}
