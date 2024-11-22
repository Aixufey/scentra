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

        // Currently only Company entity is supported
        if (entity instanceof Company company && map instanceof Company companyMap) {
            String imageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
            companyMap.setImageUrl(imageUrl);
        }

        if (entity instanceof Perfumer perfumer && map instanceof Perfumer perfumerMap) {
            String imageUrl = urlBuilder(cdnConfig.getPerfumerUrl(), perfumer.getImageUrl());
            perfumerMap.setImageUrl(imageUrl);
            if (perfumer.getCompany() != null) {
                String companyImageUrl = urlBuilder(cdnConfig.getCompanyUrl(), perfumer.getCompany().getImageUrl());
                perfumerMap.getCompany().setImageUrl(companyImageUrl);
            }
        }

        if (entity instanceof Fragrance fragrance && map instanceof Fragrance fragranceMap) {
            String imageUrl = urlBuilder(cdnConfig.getFragranceUrl(), fragrance.getImageUrl());
            fragranceMap.setImageUrl(imageUrl);
            Brand brand = fragrance.getBrand();
            Country country = fragrance.getCountry();

            if (brand != null) {

                if (brand.getCountry() != null) {
                    // TODO: Add brand's country image URL
                }
                if (brand.getCompany() != null) {
                    String brandCompanyUrl = urlBuilder(cdnConfig.getCompanyUrl(), brand.getCompany().getImageUrl());
                    brand.getCompany().setImageUrl(brandCompanyUrl);
                }
            }
            if (country != null) {
                // TODO: Add country image URL
            }
        }

        return map;
    }

    private String urlBuilder(String path, String imageName) {
        return cdnConfig.getBaseUrl() + path + "/" + imageName;
    }
}
