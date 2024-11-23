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

        // Fragrance
        if (entity instanceof Fragrance fragrance && map instanceof Fragrance fragranceMap) {
            String imageUrl = urlBuilder(cdnConfig.getFragranceUrl(), fragrance.getImageUrl());
            fragranceMap.setImageUrl(imageUrl);
            Brand brand = fragrance.getBrand();
            Country country = fragrance.getCountry();
            if (brand != null) {
                String brandImageUrl = urlBuilder(cdnConfig.getBrandUrl(), brand.getImageUrl());
                fragranceMap.getBrand().setImageUrl(brandImageUrl);
                if (brand.getCountry() != null) {
                    String brandCountryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), brand.getCountry().getImageUrl());
                    fragranceMap.getBrand().getCountry().setImageUrl(brandCountryImageUrl);
                }
                if (brand.getCompany() != null) {
                    String companyImageUrl = urlBuilder(cdnConfig.getCompanyUrl(), brand.getCompany().getImageUrl());
                    fragranceMap.getBrand().getCompany().setImageUrl(companyImageUrl);
                    Company company = brand.getCompany();
                    if (company.getCountry() != null) {
                        String companyCountryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), company.getCountry().getImageUrl());
                        fragranceMap.getBrand().getCompany().getCountry().setImageUrl(companyCountryImageUrl);
                    }
                }
            }
            if (country != null) {
                String countryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), country.getImageUrl());
                fragranceMap.getCountry().setImageUrl(countryImageUrl);
            }
        }

        // Perfumer
        if (entity instanceof Perfumer perfumer && map instanceof Perfumer perfumerMap) {
            String imageUrl = urlBuilder(cdnConfig.getPerfumerUrl(), perfumer.getImageUrl());
            perfumerMap.setImageUrl(imageUrl);
            Company company = perfumer.getCompany();
            Country country = perfumer.getCountry();

            if (company != null) {
                String companyImageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
                perfumerMap.getCompany().setImageUrl(companyImageUrl);
                Country companyCountry = company.getCountry();
                if (companyCountry != null) {
                    String companyCountryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), companyCountry.getImageUrl());
                    perfumerMap.getCompany().getCountry().setImageUrl(companyCountryImageUrl);
                }
            }

            if (country != null) {
                String countryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), country.getImageUrl());
                perfumerMap.getCountry().setImageUrl(countryImageUrl);
            }
        }

        // Company
        if (entity instanceof Company company && map instanceof Company companyMap) {
            String imageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
            companyMap.setImageUrl(imageUrl);
            Country country = company.getCountry();
            if (country != null) {
                String countryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), country.getImageUrl());
                companyMap.getCountry().setImageUrl(countryImageUrl);
            }
        }

        // Brand
        if (entity instanceof Brand brand && map instanceof Brand brandMap) {
            String imageUrl = urlBuilder(cdnConfig.getBrandUrl(), brand.getImageUrl());
            brandMap.setImageUrl(imageUrl);
            Country country = brand.getCountry();
            Company company = brand.getCompany();

            if (country != null) {
                String countryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), country.getImageUrl());
                brandMap.getCountry().setImageUrl(countryImageUrl);
            }
            if (company != null) {
                String companyImageUrl = urlBuilder(cdnConfig.getCompanyUrl(), company.getImageUrl());
                brandMap.getCompany().setImageUrl(companyImageUrl);
                Country companyCountry = company.getCountry();
                if (companyCountry != null) {
                    String companyCountryImageUrl = urlBuilder(cdnConfig.getCountryUrl(), companyCountry.getImageUrl());
                    brandMap.getCompany().getCountry().setImageUrl(companyCountryImageUrl);
                }
            }
        }

        // Country, temporary using FlagsAPI
        if (entity instanceof Country country && map instanceof Country countryMap) {
            //String imageUrl = urlBuilder(cdnConfig.getCountryUrl(), country.getImageUrl());
            String imageUrl = cdnConfig.getCountryUrl() + country.getImageUrl();
            countryMap.setImageUrl(imageUrl);
        }

        return map;
    }

    private String urlBuilder(String path, String imageName) {
        if (imageName != null && !imageName.startsWith(cdnConfig.getBaseUrl())) {
            return cdnConfig.getBaseUrl() + path + "/" + imageName;
        }
        return imageName;
    }
}
