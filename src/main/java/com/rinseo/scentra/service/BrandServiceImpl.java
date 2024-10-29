package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.BrandNotFoundException;
import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;
import com.rinseo.scentra.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repo;

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
    public BrandDTO update(long id, BrandDTO brand) {
        Brand foundBrand = repo.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand by id " + id + " was not found"));
        foundBrand.setName(brand.name());
        Brand updatedBrand = repo.saveAndFlush(foundBrand);

        return new BrandDTO(updatedBrand.getId(), updatedBrand.getName());
    }

    @Override
    public BrandDTO create(BrandDTO brand) {
        Brand brandEntity = new ModelMapper().map(brand, Brand.class);
        Brand savedBrand = repo.saveAndFlush(brandEntity);

        return new BrandDTO(savedBrand.getId(), savedBrand.getName());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
