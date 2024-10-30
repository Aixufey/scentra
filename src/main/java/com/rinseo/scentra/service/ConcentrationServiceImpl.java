package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.ConcentrationNotFoundException;
import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.dto.ConcentrationDTO;
import com.rinseo.scentra.repository.ConcentrationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConcentrationServiceImpl implements ConcentrationService {
    private final ConcentrationRepository repo;
    private final ModelMapper modelMapper;

    @Override
    public List<Concentration> getAll() {
        return repo.findAll();
    }

    @Override
    public Concentration getById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ConcentrationNotFoundException("Concentration with id " + id + " not found"));
    }

    @Override
    public ConcentrationDTO create(ConcentrationDTO concentration) {
        Concentration concentrationEntity = modelMapper.map(concentration, Concentration.class);
        Concentration savedConcentration = repo.saveAndFlush(concentrationEntity);

        return new ConcentrationDTO(
                savedConcentration.getId(),
                savedConcentration.getName(),
                savedConcentration.getDescription());
    }

    @Override
    public ConcentrationDTO update(long id, ConcentrationDTO concentration) {
        Concentration foundConcentration = repo.findById(id)
                .orElseThrow(() -> new ConcentrationNotFoundException("Concentration with id " + id + " not found"));
        foundConcentration.setName(concentration.name());
        foundConcentration.setDescription(concentration.description());
        Concentration updatedConcentration = repo.saveAndFlush(foundConcentration);

        return new ConcentrationDTO(
                updatedConcentration.getId(),
                updatedConcentration.getName(),
                updatedConcentration.getDescription());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
