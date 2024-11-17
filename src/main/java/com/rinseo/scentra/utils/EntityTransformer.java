package com.rinseo.scentra.utils;

import com.rinseo.scentra.configuration.CDNConfig;
import com.rinseo.scentra.model.Company;
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

        return map;
    }

    private String urlBuilder(String path, String imageName) {
        return cdnConfig.getBaseUrl() + path + "/" + imageName;
    }
}
