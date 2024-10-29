package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Brand getById(long id);

    BrandDTO update(long id, BrandDTO brand);

    BrandDTO create(BrandDTO brand);

    void delete(long id);
}
