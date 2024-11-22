package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.dto.BrandDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Brand getById(long id);

    Brand getByName(String name);

    Brand update(long id, BrandDTO brand, MultipartFile file);

    Brand create(BrandDTO brand, MultipartFile file);

    void delete(long id);
}
