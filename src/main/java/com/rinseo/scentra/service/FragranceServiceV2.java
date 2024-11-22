package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FragranceServiceV2 {
    List<Fragrance> getAll();

    Fragrance getById(long id);

    List<Fragrance> getByName(String name);

    Fragrance create(FragranceDTO fragrance, MultipartFile file);

    Fragrance update(long id, FragranceDTO fragrance, MultipartFile file);

    void deleteById(long id);
}
