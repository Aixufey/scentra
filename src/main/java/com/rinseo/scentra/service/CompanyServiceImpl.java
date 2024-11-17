package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.CompanyNotFoundException;
import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;
import com.rinseo.scentra.repository.CompanyRepository;
import com.rinseo.scentra.utils.EntityTransformer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repo;
    private final ModelMapper modelMapper;
    private final EntityTransformer entityTransformer;

    @Override
    public List<Company> getAll() {
        List<Company> companies = repo.findAll();
        return companies.stream()
                .map(c -> entityTransformer
                        .mapEntityUrl(c, Company.class))
                .collect(Collectors.toList());
    }

    @Override
    public Company getById(long id) {
        Company company = repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
        return entityTransformer.mapEntityUrl(company, Company.class);
    }

    @Override
    public CompanyDTO create(CompanyDTO company) {
        Company companyEntity = modelMapper.map(company, Company.class);

        if (company.imageUrl() == null || company.imageUrl().isBlank()) {
            companyEntity.setImageUrl("company_jfnnwj.png");
        }

        Company savedCompany = repo.saveAndFlush(companyEntity);

        // CDN has to be configured to upload images
        return new CompanyDTO(savedCompany.getId(), savedCompany.getName(), savedCompany.getImageUrl());
    }

    @Override
    public CompanyDTO update(long id, CompanyDTO company) {
        Company foundCompany = repo.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
        foundCompany.setName(company.name());

        if (company.imageUrl() != null && !company.imageUrl().isBlank()) {
            foundCompany.setImageUrl(company.imageUrl());
        }

        Company updatedCompany = repo.saveAndFlush(foundCompany);

        return new CompanyDTO(updatedCompany.getId(), updatedCompany.getName(), updatedCompany.getImageUrl());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
