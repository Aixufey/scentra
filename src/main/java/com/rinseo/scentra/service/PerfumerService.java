package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PerfumerService {
    List<Perfumer> getAll();

    Perfumer getById(long id);

    List<Perfumer> getByName(String name);

    Perfumer create(PerfumerDTO perfumer, MultipartFile file);

    Perfumer update(long id, PerfumerDTO perfumer, MultipartFile file);

    void deleteById(long id);
}
