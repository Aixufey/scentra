package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.CompanyNotFoundException;
import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;
import com.rinseo.scentra.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repo;
    private final ModelMapper modelMapper;

    @Override
    public List<Company> getAll() {
        return repo.findAll();
    }

    @Override
    public Company getById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
    }

    @Override
    public CompanyDTO create(CompanyDTO company) {
        Company companyEntity = modelMapper.map(company, Company.class);
        Company savedCompany = repo.saveAndFlush(companyEntity);

        return new CompanyDTO(savedCompany.getId(), savedCompany.getName());
    }

    @Override
    public CompanyDTO update(long id, CompanyDTO company) {
        Company foundCompany = repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
        foundCompany.setName(company.name());
        Company updatedCompany = repo.saveAndFlush(foundCompany);

        return new CompanyDTO(updatedCompany.getId(), updatedCompany.getName());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
