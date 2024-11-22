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
import com.rinseo.scentra.utils.EntityTransformer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repo;
    private final CountryRepository countryRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final EntityTransformer entityTransformer;

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = repo.findAll();
        return brands.stream()
                .map(b -> entityTransformer.mapEntityUrl(b, Brand.class))
                .collect(Collectors.toList());
    }

    @Override
    public Brand getById(long id) {
        Brand brand = repo.findById(id).orElseThrow(() ->
                new BrandNotFoundException("Brand with id " + id + " not found"));
        return entityTransformer.mapEntityUrl(brand, Brand.class);
    }

    @Override
    @Transactional
    public Brand update(long id, BrandDTO brand, MultipartFile file) {
        Brand foundBrand = repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand by id " + id + " was not found"));

        // Before updating, check if brand with the same name already exists
        Brand brandExist = repo.findByNameEqualsIgnoreCase(brand.getName());
        if (brandExist != null) {
            throw new IllegalArgumentException("Brand with name " + brand.getName() + " already exists");
        }
        foundBrand.setName(brand.getName());

//        if (brand.imageUrl() != null && !brand.imageUrl().isBlank()) {
//            foundBrand.setImageUrl(brand.imageUrl());
//        }

        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/brand");
            foundBrand.setImageUrl(publicId);
        }

        updateMetadata(brand, foundBrand);

        return repo.saveAndFlush(foundBrand);
    }

    private void updateMetadata(BrandDTO brandData, Brand brandEntity) {
        Country country = countryRepository.findById(brandData.getCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with id " + brandData.getCountryId() + " not found"));
        brandEntity.setCountry(country);

        if (brandData.getCompanyId() != null) {
            Company company = companyRepository.findById(brandData.getCompanyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company with id " + brandData.getCompanyId() + " not found"));
            brandEntity.setCompany(company);
        }
    }

    @Override
    public Brand getByName(String name) {
        Brand brand = repo.findByNameEqualsIgnoreCase(name);
        if (brand == null) {
            throw new BrandNotFoundException("Brand with name " + name + " not found");
        }
        return entityTransformer.mapEntityUrl(brand, Brand.class);
    }

    @Override
    @Transactional
    public Brand create(BrandDTO brand, MultipartFile file) {
        Brand brandEntity = modelMapper.map(brand, Brand.class);

        // check if brand already exists
        if (repo.findByNameEqualsIgnoreCase(brand.getName()) != null) {
            throw new IllegalArgumentException("Brand with name " + brand.getName() + " already exists");
        }

//        if (brand.imageUrl() == null || brand.imageUrl().isBlank()) {
//            brandEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731784429/scentra/brand_dubl8q_b_rgb_FFFFFF_hvsst6.png");
//        }
        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/brand");
            brandEntity.setImageUrl(publicId);
        } else {
            brandEntity.setImageUrl("brand_dubl8q.png");
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
