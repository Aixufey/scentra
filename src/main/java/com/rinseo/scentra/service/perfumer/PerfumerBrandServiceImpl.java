package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.exception.BrandNotFoundException;
import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Brand;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.repository.BrandRepository;
import com.rinseo.scentra.repository.PerfumerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PerfumerBrandServiceImpl implements PerfumerBrandService {
    private final PerfumerRepository perfumerRepository;
    private final BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Brand> getAll(long perfumerId) {
        // JPA cannot fetch the brands owned by the perfumer for unknown reason.
        // Temporary solution is using EntityManager to fetch the brands manually.
        // Adding One-to-Many and Many-to-One fixed this issue.
        //List<Brand> perfumerBrands = getBrands(perfumerId);

        Perfumer perfumer = getPerfumer(perfumerId);

        return List.copyOf(perfumer.getBrands());
    }

    @Override
    @Transactional
    public List<Brand> updateBrand(long perfumerId, long brandId) {
        // Find perfumer
        Perfumer perfumer = getPerfumer(perfumerId);
        // Find existing brand to add
        Brand brand = getBrand(brandId);

        Set<Brand> brands = perfumer.getBrands();
        brands.add(brand);
        perfumer.setBrands(brands);

        perfumerRepository.save(perfumer);

        return List.copyOf(brands);
    }

    @Override
    @Transactional
    public void deleteBrand(long perfumerId, long brandId) {
        // Find perfumer
        Perfumer perfumer = getPerfumer(perfumerId);
        // Find existing brand to remove
        Brand brand = getBrand(brandId);

        Set<Brand> brands = perfumer.getBrands();
        brands.remove(brand);
        perfumer.setBrands(brands);

        perfumerRepository.save(perfumer);
    }

    @Override
    @Transactional
    public void deleteAll(long perfumerId) {
        Perfumer perfumer = getPerfumer(perfumerId);

        // Remove perfumer from each brand's collection
        Set<Brand> brands = perfumer.getBrands();
        for (var brand : brands) {
            brand.getPerfumers().remove(perfumer);
            brandRepository.saveAndFlush(brand);
        }
        // Clear perfumer's collection of brands
        perfumer.getBrands().clear();

        perfumerRepository.save(perfumer);
    }

    private List<Brand> getBrands(long perfumerId) {
        String jpql = """
                SELECT b FROM Brand b
                JOIN b.perfumers p
                WHERE p.id = :perfumerId
                """;
        return entityManager.createQuery(jpql, Brand.class)
                .setParameter("perfumerId", perfumerId)
                .getResultList();
    }

    public Brand getBrand(long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + brandId));
        return brand;
    }

    private Perfumer getPerfumer(long perfumerId) {
        return perfumerRepository.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer not found with ID: " + perfumerId));
    }
}
