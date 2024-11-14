package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.exception.BrandNotFoundException;
import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.repository.BrandRepository;
import com.rinseo.scentra.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class FragranceBrandServiceImpl implements FragranceBrandService {
    private final FragranceRepository fragranceRepository;
    private final BrandRepository brandRepository;

    @Override
    public Brand getBrand(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getBrand() == null) {
            throw new FragranceNotFoundException("Fragrance with id " + fragranceId + " has no brand");
        }

        return fragrance.getBrand();
    }

    @Override
    @Transactional
    public Brand updateBrand(long fragranceId, long brandId) {
        Fragrance fragrance = getFragrance(fragranceId);
        Brand brand = getFragranceBrand(brandId);

        fragrance.setBrand(brand);

        Set<Fragrance> fragrances = brand.getFragrances();
        fragrances.add(fragrance);
        brand.setFragrances(fragrances);

        fragranceRepository.saveAndFlush(fragrance);
        brandRepository.saveAndFlush(brand);

        return brand;
    }

    @Override
    @Transactional
    public void deleteBrand(long fragranceId) {
        Fragrance fragrance = getFragrance(fragranceId);

        if (fragrance.getBrand() == null) {
            throw new BrandNotFoundException("Fragrance with id " + fragranceId + " has no brand");
        }

        fragrance.setBrand(null);

        fragranceRepository.saveAndFlush(fragrance);
    }

    private Fragrance getFragrance(long fragranceId) {
        return fragranceRepository.findById(fragranceId)
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + fragranceId + " not found"));
    }

    private Brand getFragranceBrand(long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + brandId + " not found"));
    }
}
