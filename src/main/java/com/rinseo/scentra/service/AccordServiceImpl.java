package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.AccordNotFoundException;
import com.rinseo.scentra.model.Accord;
import com.rinseo.scentra.model.dto.AccordDTO;
import com.rinseo.scentra.repository.AccordRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccordServiceImpl implements AccordService {
    private final AccordRepository repo;

    @Override
    public List<Accord> getAll() {
        return repo.findAll();
    }

    @Override
    public Accord getById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new AccordNotFoundException("Accord not found with id: " + id));
    }

    @Override
    public AccordDTO create(AccordDTO accord) {
        Accord accordEntity = new ModelMapper().map(accord, Accord.class);
        Accord savedAccord = repo.saveAndFlush(accordEntity);

        return new AccordDTO(savedAccord.getId(), savedAccord.getName(), savedAccord.getDescription());
    }

    @Override
    public AccordDTO update(long id, AccordDTO accord) {
        Accord foundAccord = repo.findById(id)
                .orElseThrow(() -> new AccordNotFoundException("Accord not found with id: " + id));
        foundAccord.setName(accord.name());
        foundAccord.setDescription(accord.description());
        Accord updatedAccord = repo.saveAndFlush(foundAccord);

        return new AccordDTO(updatedAccord.getId(), updatedAccord.getName(), updatedAccord.getDescription());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
