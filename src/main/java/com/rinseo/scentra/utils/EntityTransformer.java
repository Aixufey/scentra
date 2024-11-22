package com.rinseo.scentra.utils;

import com.rinseo.scentra.configuration.CDNConfig;
import com.rinseo.scentra.model.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Utility class to transform an entity to the target class and build the image URL
 */
@Component
@AllArgsConstructor
public class EntityTransformer {
    private final ModelMapper modelMapper;
    private final CDNConfig cdnConfig;

    /**
     * Map entity to target class and append CDN base URL to image URL
     */
    public <T> T mapEntityUrl(T entity, Class<T> target) {
        T map = modelMapper.map(entity, target);

        if (entity instanceof Company company && map instanceof Company companyMap) {
            String imageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
            companyMap.setImageUrl(imageUrl);
            Country country = company.getCountry();

            if (country != null) {
                // TODO: Add country image URL
            }
        }

        if (entity instanceof Perfumer perfumer && map instanceof Perfumer perfumerMap) {
            String imageUrl = urlBuilder(cdnConfig.getPerfumerUrl(), perfumer.getImageUrl());
            perfumerMap.setImageUrl(imageUrl);
            Company company = perfumer.getCompany();
            Country country = perfumer.getCountry();

            if (company != null) {
                String companyImageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
                perfumerMap.getCompany().setImageUrl(companyImageUrl);
                if (company.getCountry() != null) {
                    // TODO: Add company's country image URL
                }
            }
            if (country != null) {
                // TODO: Add country image URL
            }

        }

        if (entity instanceof Fragrance fragrance && map instanceof Fragrance fragranceMap) {
            String imageUrl = urlBuilder(cdnConfig.getFragranceUrl(), fragrance.getImageUrl());
            fragranceMap.setImageUrl(imageUrl);
            Brand brand = fragrance.getBrand();
            Country country = fragrance.getCountry();

            if (brand != null) {
                String brandURL = urlBuilder(cdnConfig.getBrandUrl(), brand.getImageUrl());
                fragranceMap.getBrand().setImageUrl(brandURL);

                if (brand.getCountry() != null) {
                    // TODO: Add brand's country image URL
                }
                if (brand.getCompany() != null) {
                    String brandCompanyURL = urlBuilder(cdnConfig.getCompanyUrl(), brand.getCompany().getImageUrl());
                    fragranceMap.getBrand().getCompany().setImageUrl(brandCompanyURL);
                }
            }
            if (country != null) {
                // TODO: Add country image URL
            }
        }

        if (entity instanceof Brand brand && map instanceof Brand brandMap) {
            String imageUrl = urlBuilder(cdnConfig.getBrandUrl(), brand.getImageUrl());
            brandMap.setImageUrl(imageUrl);
            Country country = brand.getCountry();
            Company company = brand.getCompany();
            if (country != null) {
                // TODO: Add country image URL
            }
            if (company != null) {
                String brandCompanyURL = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
                brandMap.getCompany().setImageUrl(brandCompanyURL);
                if (company.getCountry() != null) {
                    // TODO: Add company's country image URL
                }
            }
        }

        return map;
    }

    private String urlBuilder(String path, String imageName) {
        return cdnConfig.getBaseUrl() + path + "/" + imageName;
    }
}
