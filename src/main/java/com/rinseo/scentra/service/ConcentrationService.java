package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.dto.ConcentrationDTO;

import java.util.List;

public interface ConcentrationService {
    List<Concentration> getAll();

    Concentration getById(long id);

    ConcentrationDTO create(ConcentrationDTO concentration);

    ConcentrationDTO update(long id, ConcentrationDTO concentration);

    void delete(long id);
}
