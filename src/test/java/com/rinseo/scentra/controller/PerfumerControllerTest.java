package com.rinseo.scentra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.PerfumerDTO;
import com.rinseo.scentra.service.PerfumerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PerfumerController.class)
@MockBean({PerfumerServiceImpl.class})
class PerfumerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PerfumerServiceImpl service;

    @Test
    @DisplayName("Perfumer can be saved")
    void testSavePerfumer_whenPerfumerIsValid_thenReturnPerfumerDetails() throws Exception {
        // Arrange
        PerfumerDTO perfumerDTO = new PerfumerDTO(1L, "Test Perfumer");
        Perfumer perfumerRequest = new ModelMapper().map(perfumerDTO, Perfumer.class);

        when(service.create(any(PerfumerDTO.class))).thenReturn(perfumerDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/perfumers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(perfumerRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBody = mvcResult.getRequest().getContentAsString();

        Perfumer createdPerfumer = new ObjectMapper().readValue(responseBody, Perfumer.class);

        // Assert
        Assertions.assertEquals(perfumerRequest.getName(), createdPerfumer.getName(),
                "The request name and response name should be the same");
    }
}
