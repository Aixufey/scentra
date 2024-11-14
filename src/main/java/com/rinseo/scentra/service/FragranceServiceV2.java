package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;

import java.util.List;

public interface FragranceServiceV2 {
    List<Fragrance> getAll();

    Fragrance getById(long id);

    List<Fragrance> getByName(String name);

    Fragrance create(FragranceDTO fragrance);

    Fragrance update(long id, FragranceDTO fragrance);

    void deleteById(long id);
}
