package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.repository.PerfumerRepository;
import com.rinseo.scentra.service.perfumer.PerfumerBrandServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCompanyServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCountryServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerFragranceServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    @Transactional
    public Perfumer create(PerfumerDTO perfumer) {
        Perfumer perfumerEntity = modelMapper.map(perfumer, Perfumer.class);
        Perfumer byName = repo.findByNameEqualsIgnoreCase(perfumer.name());

        if (byName != null) {
            throw new UniqueViolationException("Perfumer with name: " + perfumer.name() + " already exists");
        }

        if (perfumerEntity.getImageUrl() == null || perfumerEntity.getImageUrl().isBlank()) {
            perfumerEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731762256/scentra/perfumer/perfumer_swoc8s.png");
        }

        updateMetadata(perfumer, perfumerEntity);

        return repo.saveAndFlush(perfumerEntity);
    }

    private void updateMetadata(PerfumerDTO perfumer, Perfumer perfumerEntity) {
        if (perfumer.companyId() != null) {
            Company company = perfumerCompanyService.getPerfumerCompany(perfumer.companyId());
            perfumerEntity.setCompany(company);
        }
        if (perfumer.countryId() != null) {
            Country country = perfumerCountryService.getPerfumerCountry(perfumer.countryId());
            perfumerEntity.setCountry(country);
        }
        if (perfumer.fragranceIds() != null) {
            // If ids are provided, fetch the fragrances and set them
            Set<Fragrance> fragrances = perfumer.fragranceIds()
                    .stream()
                    .map(perfumerFragranceService::getFragrance)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            perfumerEntity.setFragrances(fragrances);

            // Since we are doing creation from inverse side (Perfumer's fragrance collection)
            // We need to update the owning side (Fragrance's perfumer collection) because of bidirectional relationship
            // For each fragrance, associate the perfumer
            for (var fragrance : fragrances) {
                Set<Perfumer> perfumers = fragrance.getPerfumers();
                perfumers.add(perfumerEntity);
                fragrance.setPerfumers(perfumers);
            }
        }
        if (perfumer.brandIds() != null) {
            // Since Perfumer manages the relationship with brand, Hibernate will insert the relationship
            Set<Brand> brands = perfumer.brandIds()
                    .stream()
                    .map(perfumerBrandService::getBrand)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            perfumerEntity.setBrands(brands);
        }
    }

    @Override
    @Transactional
    public Perfumer update(long id, PerfumerDTO perfumer) {
        Perfumer foundPerfumer = repo.findById(id)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        foundPerfumer.setName(perfumer.name());

        return repo.saveAndFlush(foundPerfumer);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        perfumerFragranceService.deleteAll(id);
        perfumerBrandService.deleteAll(id);
        perfumerCountryService.deleteCountry(id);
        perfumerCompanyService.deleteCompany(id);

        repo.deleteById(id);
    }
}
