package com.rinseo.scentra.service;

import com.rinseo.scentra.configuration.CDNConfig;
import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.exception.UniqueViolationException;
import com.rinseo.scentra.model.*;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.repository.PerfumerRepository;
import com.rinseo.scentra.service.perfumer.PerfumerBrandServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCompanyServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerCountryServiceImpl;
import com.rinseo.scentra.service.perfumer.PerfumerFragranceServiceImpl;
import com.rinseo.scentra.utils.EntityTransformer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final CloudinaryServiceImpl cloudinaryService;
    private final EntityTransformer entityTransformer;

    @Override
    public List<Perfumer> getAll() {
        List<Perfumer> perfumers = repo.findAll();
        return perfumers.stream()
                .map(p -> entityTransformer
                        .mapEntityUrl(p, Perfumer.class))
                .collect(Collectors.toList());
    }

    @Override
    public Perfumer getById(long id) {
        Perfumer perfumer = repo.findById(id).orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        return entityTransformer.mapEntityUrl(perfumer, Perfumer.class);
    }

    @Override
    public List<Perfumer> getByName(String name) {
        List<Perfumer> perfumers = repo.findByNameContainingIgnoreCase(name);
        return perfumers.stream()
                .map(p -> entityTransformer
                        .mapEntityUrl(p, Perfumer.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Perfumer create(PerfumerDTO perfumer, MultipartFile file) {
        Perfumer perfumerEntity = modelMapper.map(perfumer, Perfumer.class);
        Perfumer byName = repo.findByNameEqualsIgnoreCase(perfumer.getName());

        if (byName != null) {
            throw new UniqueViolationException("Perfumer with name: " + perfumer.getName() + " already exists");
        }

//        if (perfumerEntity.getImageUrl() == null || perfumerEntity.getImageUrl().isBlank()) {
//            perfumerEntity.setImageUrl("https://res.cloudinary.com/dx09tdgnz/image/upload/v1731762256/scentra/perfumer/perfumer_swoc8s.png");
//        }
        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/perfumer");
            perfumerEntity.setImageUrl(publicId);
        } else {
            // Default image
            perfumerEntity.setImageUrl("perfumer_swoc8s.png");
        }

        updateMetadata(perfumer, perfumerEntity);

        return repo.saveAndFlush(perfumerEntity);
    }

    private void updateMetadata(PerfumerDTO perfumer, Perfumer perfumerEntity) {
        if (perfumer.getCompanyId() != null) {
            Company company = perfumerCompanyService.getPerfumerCompany(perfumer.getCompanyId());
            perfumerEntity.setCompany(company);
        }
        if (perfumer.getCountryId() != null) {
            Country country = perfumerCountryService.getPerfumerCountry(perfumer.getCountryId());
            perfumerEntity.setCountry(country);
        }
        if (perfumer.getFragranceIds() != null) {
            // If ids are provided, fetch the fragrances and set them
            Set<Fragrance> fragrances = perfumer.getFragranceIds()
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
        if (perfumer.getBrandIds() != null) {
            // Since Perfumer manages the relationship with brand, Hibernate will insert the relationship
            Set<Brand> brands = perfumer.getBrandIds()
                    .stream()
                    .map(perfumerBrandService::getBrand)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            perfumerEntity.setBrands(brands);
        }
    }

    @Override
    @Transactional
    public Perfumer update(long id, PerfumerDTO perfumer, MultipartFile file) {
        Perfumer foundPerfumer = repo.findById(id)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with id: " + id));
        foundPerfumer.setName(perfumer.getName());

//        if (perfumer.getImageUrl() != null && !perfumer.getImageUrl().isBlank()) {
//            foundPerfumer.setImageUrl(perfumer.getImageUrl());
//        }
        if (file != null) {
            String publicId = cloudinaryService.uploadImageFile(file, "scentra/perfumer");
            foundPerfumer.setImageUrl(publicId);
        }

        updateMetadata(perfumer, foundPerfumer);

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
