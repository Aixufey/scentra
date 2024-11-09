package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;

import java.util.List;

public interface PerfumerService {
    List<Perfumer> getAll();

    Perfumer getById(long id);

    List<Perfumer> getByName(String name);

    PerfumerDTO create(PerfumerDTO perfumer);

    PerfumerDTO update(long id, PerfumerDTO perfumer);

    void deleteById(long id);
}
