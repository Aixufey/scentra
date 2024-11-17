package com.rinseo.scentra.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CDNConfigTest {
    @Autowired
    private CDNConfig cdnConfig;

    @Test
    void testGetBaseUrl_isNotNull() {
        String baseUrl = cdnConfig.getBaseUrl();

        Assertions.assertNotNull(baseUrl);
    }
}
