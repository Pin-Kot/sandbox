package com.minsk.frontendpracticeservice.integration.controller;

import com.minsk.frontendpracticeservice.config.IntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import com.minsk.frontendpracticeservice.stubs.RequisitesRequestDtoStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesStubs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class RequisitesControllerIntegrationTest extends IntegrationTestConfig {

    @Autowired
    private RequisitesService requisitesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private Requisites requisitesNotInBase;
    private Requisites requisitesInBase;
    private Requisites updatedRequisites;

    @BeforeEach
    void setUp() {
        requisitesNotInBase = RequisitesStubs.createNotInBaseRequisitesStubs();
        requisitesInBase = RequisitesStubs.createValidRequisitesStubs();
        updatedRequisites = RequisitesStubs.createUpdatedRequisitesStubs();
    }

    @Test
    void createRequisitesResponseTest() throws Exception {
        mockMvc.perform(post("/requisites")
                        .content(objectMapper.writeValueAsString(requisitesNotInBase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value(requisitesNotInBase.getAccountNumber()))
                .andExpect(jsonPath("$.inn").value(requisitesNotInBase.getInn()));
    }

    @Test
    void createRequisitesResponseThrowsNotAllowedToCreateRequisitesTest() throws Exception {
        mockMvc.perform(post("/requisites")
                        .content(objectMapper.writeValueAsString(requisitesInBase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Реквизиты с таким счетом уже существуют"));
    }

    @Test
    void readRequisitesResponseTest() throws Exception {
        mockMvc.perform(get("/requisites/" + requisitesInBase.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requisitesInBase.getId().toString()))
                .andExpect(jsonPath("$.accountNumber").value(requisitesInBase.getAccountNumber()))
                .andExpect(jsonPath("$.inn").value(requisitesInBase.getInn()));
    }

    @Test
    void readRequisitesResponseThrowsNotFoundTest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get("/requisites/" + nonExistentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Реквизиты с таким id %s не найдены".formatted(nonExistentId.toString())));
    }

    @Test
    void updateRequisitesResponseTest() throws Exception {
        RequisitesRequestUpdateDto validRequisitesRequestUpdateDto = RequisitesRequestDtoStubs.createRequisitesRequestUpdateDtoStubs(updatedRequisites);

        mockMvc.perform(put("/requisites")
                        .content(objectMapper.writeValueAsString(validRequisitesRequestUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedRequisites.getId().toString()))
                .andExpect(jsonPath("$.kpp").value(updatedRequisites.getKpp()))
                .andExpect(jsonPath("$.kbk").value(updatedRequisites.getKbk()))
                .andExpect(jsonPath("$.correspondentAccount").value(updatedRequisites.getCorrespondentAccount()));
    }

    @Test
    void updateRequisitesResponseThrowsNotFoundTest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        requisitesNotInBase.setId(nonExistentId);
        RequisitesRequestUpdateDto notValidRequisitesUpdateDto = RequisitesRequestDtoStubs.createRequisitesRequestUpdateDtoStubs(requisitesNotInBase);

        mockMvc.perform(put("/requisites")
                        .content(objectMapper.writeValueAsString(notValidRequisitesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Реквизиты с таким id %s не найдены".formatted(nonExistentId.toString())));
    }

    @Test
    void deleteRequisitesResponseTest() throws Exception {
        mockMvc.perform(delete("/requisites/" + requisitesInBase.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
