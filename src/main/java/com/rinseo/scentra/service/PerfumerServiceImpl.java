package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.repository.PerfumerRepository;
import com.rinseo.scentra.service.perfumer.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PerfumerServiceImpl implements PerfumerService {
    private final PerfumerRepository repo;
    private final PerfumerFragranceServiceImpl perfumerFragranceService;
    private final PerfumerBrandServiceImpl perfumerBrandService;
    private final PerfumerCountryServiceImpl perfumerCountryService;
    private final PerfumerCompanyServiceImpl perfumerCompanyService;
    private final ModelMapper modelMapper;

    @Override
    public List<Perfumer> getAll() {
        return repo.findAll();
    }

    @Override
    public Perfumer getById(long id) {
        return repo.findById(id).orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
    }

    @Override
    public List<Perfumer> getByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public PerfumerDTO create(PerfumerDTO perfumer) {
        Perfumer perfumerEntity = modelMapper.map(perfumer, Perfumer.class);
        Perfumer savedPerfumer = repo.saveAndFlush(perfumerEntity);

        return new PerfumerDTO(savedPerfumer.getId(), savedPerfumer.getName());
    }

    @Override
    public PerfumerDTO update(long id, PerfumerDTO perfumer) {
        Perfumer foundPerfumer = repo.findById(id)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        foundPerfumer.setName(perfumer.name());
        Perfumer updatedPerfumer = repo.saveAndFlush(foundPerfumer);

        return new PerfumerDTO(updatedPerfumer.getId(), updatedPerfumer.getName());
    }

    @Override
    public void deleteById(long id) {
        perfumerFragranceService.deleteAll(id);
        perfumerBrandService.deleteAll(id);
        perfumerCountryService.deleteCountry(id);
        perfumerCompanyService.deleteCompany(id);

        repo.deleteById(id);
    }
}
