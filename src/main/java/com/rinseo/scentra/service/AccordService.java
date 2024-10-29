package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Accord;
import com.rinseo.scentra.model.dto.AccordDTO;

import java.util.List;

public interface AccordService {
    List<Accord> getAll();
    Accord getById(long id);
    AccordDTO create(AccordDTO accord);
    AccordDTO update(long id, AccordDTO accord);
    void delete(long id);
}
