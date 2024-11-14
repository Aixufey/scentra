package com.rinseo.scentra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.dto.FragranceDTO;
import com.rinseo.scentra.service.FragranceServiceV2Impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

// This annotation will only scan for web layer components such as Controller and load into the Spring context
// If Spring Security is enabled, it will be excluded with excludeAutoConfiguration
@WebMvcTest(controllers = FragranceController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@MockBean({FragranceServiceV2Impl.class})
//@Import(FragranceControllerTest.TestConfig.class)
class FragranceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
//    @MockBean
    private FragranceServiceV2Impl service;
    //@Autowired
    //private ModelMapper modelMapper;

//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public ModelMapper modelMapper() {
//            ModelMapper modelMapper = new ModelMapper();
//            modelMapper.getConfiguration()
//                    .setFieldMatchingEnabled(true)
//                    .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
//            return modelMapper;
//        }
//    }

    @Test
    @DisplayName("Fragrance can be saved")
    void testSaveFragrance_whenFragranceIsValid_thenReturnFragranceDetails() throws Exception {
        // Arrange
        FragranceDTO fragranceDTO = new FragranceDTO(1L, "Test Fragrance", 2021);
        Fragrance fragranceRequest = new ModelMapper().map(fragranceDTO, Fragrance.class);

        // Integration test with service layer, the service layer is mocked
        when(service.create(any(FragranceDTO.class))).thenReturn(fragranceRequest);

        // Testing with MockMvc with a POST request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/fragrances")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fragranceRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBody = mvcResult.getRequest().getContentAsString();

        Fragrance createdFragrance = new ObjectMapper().readValue(responseBody, Fragrance.class);

        // Assert
        Assertions.assertEquals(fragranceRequest.getName(), createdFragrance.getName(),
                "The request name and response name should be the same");

        Assertions.assertEquals(fragranceRequest.getYear(), createdFragrance.getYear(),
                "The request year and response year should be the same");

    }

    @Test
    @DisplayName("Fragrance year validation")
    void testSaveFragrance_whenYearIsInvalid_thenReturnBadRequest() throws Exception {
        // Arrange
        FragranceDTO fragranceDTO = new FragranceDTO(1L, "Test Fragrance", 2027);
        Fragrance fragranceRequest = new ModelMapper().map(fragranceDTO, Fragrance.class);

        when(service.create(any(FragranceDTO.class))).thenReturn(fragranceRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/fragrances")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fragranceRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals("The status should be 400", 400, status);
    }
}
