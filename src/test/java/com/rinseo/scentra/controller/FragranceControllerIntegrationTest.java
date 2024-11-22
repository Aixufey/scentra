package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.dto.FragranceDTO;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

// This annotation will include all layers and load the application context.
// It will create a mock servlet and will not start the server unless specified.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// This defined port will override application.properties port.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

// To avoid port conflict we can use random port that will have the highest precedence.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "/application-test.properties",
        properties = "server.port=5000")
class FragranceControllerIntegrationTest {

    // Injecting the value from .properties file.
    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    // HTTP client to test the API.
    // https://howtodoinjava.com/spring-boot2/testing/testresttemplate-post-example/
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // properties has higher precedence than .properties file.
        System.out.println("Defined PORT: " + serverPort);
        System.out.println("Local PORT: " + localServerPort);
    }

    @Test
    @DisplayName("Fragrance can be created")
    void testCreateFragrance_whenValidInput_thenReturnFragranceDetails() throws JSONException {
        // Arrange

        // 1. Creating the request body
        //JSONObject fragranceJSON = new JSONObject();
        //fragranceJSON.put("id", 1L);
        //fragranceJSON.put("name", "Qaa'ed Al Shabaab");
        //fragranceJSON.put("year", 2021);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("id", 1L);
        formData.add("name", "Qaa'ed Al Shabaab");
        formData.add("year", 2021);

        // 2. Set headers
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 3. To use http client we need to create the request.
        //HttpEntity<String> request = new HttpEntity<>(fragranceJSON.toString(), headers);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, headers);

        // Act
        // Respond body will a JSON string, we will convert it back to JSON object.
        // Record DTO has issues accessing private fields, so the ModelMapper is explicit configured.
        ResponseEntity<FragranceDTO> createdEntity = restTemplate.postForEntity("/api/v1/fragrances", request, FragranceDTO.class);
        FragranceDTO fragranceDetails = createdEntity.getBody();
        HttpStatusCode statusCode = createdEntity.getStatusCode();
        HttpHeaders header = createdEntity.getHeaders();

        // Assert
        Assertions.assertEquals(HttpStatus.CREATED, statusCode);
        Assertions.assertEquals("Qaa'ed Al Shabaab", fragranceDetails.getName()
                , "Fragrance name should be Qaa'ed Al Shabaab");
        Assertions.assertEquals(MediaType.APPLICATION_JSON, header.getContentType());
    }
}

