package com.rinseo.scentra.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CDNConfiguration {
    @Value("${cdn.base.url}")
    private String baseUrl;
}
