package com.minsk.frontendpracticeservice.integration.controller;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.exception.server.ServerErrorException;
import com.minsk.frontendpracticeservice.exception.server.ServiceUnavailableException;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import com.minsk.frontendpracticeservice.stubs.RequisitesStubs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExceptionControllerIntegrationTest {

    @MockitoBean
    private RequisitesService requisitesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private Requisites requisitesNotInBase;

    @BeforeEach
    void setUp() {
        requisitesNotInBase = RequisitesStubs.createNotInBaseRequisitesStubs();
    }

    @Test
    void createRequisitesThrowServerErrorTest() throws Exception {

        Mockito.when(requisitesService.createRequisites(Mockito.any(RequisitesCreateRequestDto.class))).thenThrow(new ServerErrorException("Произошла непредвиденная ошибка сервера"));

        mockMvc.perform(post("/requisites")
                        .content(objectMapper.writeValueAsString(requisitesNotInBase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Произошла непредвиденная ошибка сервера"));
    }

    @Test
    void createRequisitesThrowServiceUnavailableErrorTest() throws Exception {

        Mockito.when(requisitesService.createRequisites(Mockito.any(RequisitesCreateRequestDto.class))).thenThrow(new ServiceUnavailableException("Сервис временно недоступен"));

        mockMvc.perform(post("/requisites")
                        .content(objectMapper.writeValueAsString(requisitesNotInBase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Сервис временно недоступен"));
    }

}
