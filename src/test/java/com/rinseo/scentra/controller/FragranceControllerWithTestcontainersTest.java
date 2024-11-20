package com.rinseo.scentra.controller;

import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Testcontainer manages the lifecycle of services inside Docker containers annotated with @Container
// It integrates with JUnit and will spin up Postgres container before the test starts
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "spring.jpa.show-sql=true"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// JUnit5 creates new instance of the test class per method by default,
// we want to share this instance such as authorizations across apps.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FragranceControllerWithTestcontainersTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection // Will use default test values provided by the container
    private final static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:16-alpine");
//                    .withDatabaseName("scentra")
//                    .withUsername("scentra")
//                    .withPassword("scentra");

    static {
        // Manually start the container since we are using the shared instance lifecycle
        // else App will try to connect to the container before it starts
        postgresContainer.start();
    }

    /**
     * Manually override the properties in the Spring context with the ones from the testcontainer
     * Can be skipped if using @ServiceConnection on @Container
     */
    @DynamicPropertySource
    private static void overrideProperties(DynamicPropertyRegistry registry) {
        // #spring.datasource.url=jdbc:postgresql://localhost:5432/scentra
        // Postgres runs on 5432, in case of conflict, we can override it with a random port from the testcontainer
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);

        // Inject username and password we configured in the container
        //registry.add("spring.datasource.username", postgresContainer::getUsername);
        //registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Order(1)
    @Test
    @DisplayName("PostgreSQL container is created and running")
    void testContainerIsRunning() {
        Assertions.assertTrue(postgresContainer.isCreated(), "Postgres container is not created");
        Assertions.assertTrue(postgresContainer.isRunning(), "Postgres container is not running");
    }

    @Order(3)
    @Test
    @DisplayName("Fragrance can be created")
    void testCreateFragrance_whenValidInput_thenReturnFragranceDetails() throws JSONException {
        // Arrange
        JSONObject fragranceJSON = new JSONObject();
        fragranceJSON.put("name", "Acqua di Giò Profumo");
        fragranceJSON.put("year", 2015);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(fragranceJSON.toString(), headers);

        // Act
        ResponseEntity<FragranceDTO> createdEntity = restTemplate.postForEntity("/api/v1/fragrances", request, FragranceDTO.class);
        FragranceDTO fragranceDetails = createdEntity.getBody();
        HttpStatusCode statusCode = createdEntity.getStatusCode();
        HttpHeaders header = createdEntity.getHeaders();

        // Assert
        assertEquals(HttpStatus.CREATED, statusCode);
        assertEquals("Acqua di Giò Profumo", fragranceDetails.name()
                , "Fragrance name should be Acqua di Giò Profumo");
        assertEquals(MediaType.APPLICATION_JSON, header.getContentType());
    }

    @Order(2)
    @Test
    @DisplayName("Perfumer can be created")
    void testCreatePerfumer() throws JSONException {
        // Arrange
        //JSONObject perfumerJSON = new JSONObject();
        //perfumerJSON.put("name", "Alberto Morillas");

        // Create a form data - Database is empty, so only testing name
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("name", "Alberto Morillas");


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA));
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        //HttpEntity<String> request = new HttpEntity<>(perfumerJSON.toString(), httpHeaders);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, httpHeaders);

        // Act
        ResponseEntity<PerfumerDTO> createdEntity = restTemplate.exchange("/api/v1/perfumers",
                HttpMethod.POST,
                request,
                PerfumerDTO.class);

        HttpStatusCode statusCode = createdEntity.getStatusCode();
        PerfumerDTO perfumerDetails = createdEntity.getBody();

        // Assert
        assertEquals(HttpStatus.CREATED, statusCode);
        assertEquals(perfumerDetails.getName(), "Alberto Morillas");
    }
}
