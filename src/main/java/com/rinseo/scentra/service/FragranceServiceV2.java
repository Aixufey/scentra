package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.service.relation.*;

import java.util.List;

public interface FragranceServiceV2 extends
        BrandRelationService,
        CountryRelationService,
        PerfumerRelationService,
        ConcentrationRelationService,
        NoteRelationService {
    List<Fragrance> getAll();

    Fragrance getById(long id);

    List<Fragrance> getByName(String name);

    FragranceDTO create(FragranceDTO fragrance);

    FragranceDTO update(long id, FragranceDTO fragrance);

    void deleteById(long id);
}
