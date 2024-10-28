package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;

import java.util.List;

public interface FragranceServiceV2 {
    List<Fragrance> getAll();

    Fragrance getById(long id);

    List<Fragrance> getByName(String name);

    FragranceDTO create(FragranceDTO fragrance);

    FragranceDTO update(long id, FragranceDTO fragrance);

    void deleteById(long id);
}
