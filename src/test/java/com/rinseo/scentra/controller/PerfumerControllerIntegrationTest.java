package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.dto.PerfumerDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PerfumerControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Perfumer can be created")
    void testCreatePerfumer() throws JSONException {
        // Arrange
        JSONObject perfumerJSON = new JSONObject();
        perfumerJSON.put("id", 1L);
        perfumerJSON.put("name", "Jo Malone");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(perfumerJSON.toString(), httpHeaders);

        // Act
        ResponseEntity<PerfumerDTO> createdEntity = restTemplate.exchange("/api/v1/perfumers",
                HttpMethod.POST,
                request,
                PerfumerDTO.class);

        HttpStatusCode statusCode = createdEntity.getStatusCode();
        PerfumerDTO perfumerDetails = createdEntity.getBody();

        // Assert
        Assertions.assertEquals(HttpStatus.CREATED, statusCode);
        Assertions.assertEquals(perfumerDetails.name(), "Jo Malone");
    }

}
