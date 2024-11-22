package com.rinseo.scentra.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * CDNConfig
 * Configuring the base URL and relative path for the image url to be dynamic transformed to the full CDN URL.
 * CDN takes variables from the <strong>application.properties</strong> making it easier to maintain and scale.
 */
@Getter
@Configuration
public class CDNConfig {
    @Value("${cdn.base.url}")
    private String baseUrl;

    @Value("${cdn.company}")
    private String companyUrl;

    @Value("${cdn.perfumer}")
    private String perfumerUrl;

    @Value("${cdn.fragrance}")
    private String fragranceUrl;

    @Value("${cdn.brand}")
    private String brandUrl;

    @Value("${cdn.note}")
    private String noteUrl;

    @Value("${cdn.concentration}")
    private String concentrationUrl;

    @Value("${cdn.country}")
    private String countryUrl;

    public String getBaseUrl(String relativePath) {
        return baseUrl + relativePath;
    }
}
