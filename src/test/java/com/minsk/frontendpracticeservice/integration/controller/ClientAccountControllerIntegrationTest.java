package com.minsk.frontendpracticeservice.integration.controller;

import com.minsk.frontendpracticeservice.config.MongoIntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import com.minsk.frontendpracticeservice.domain.request.AccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;
import com.minsk.frontendpracticeservice.repository.ClientAccountRepository;
import com.minsk.frontendpracticeservice.stubs.ClientAccountRequestDtoStubs;
import com.minsk.frontendpracticeservice.stubs.ClientAccountStubs;
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

import java.util.List;

import static com.minsk.frontendpracticeservice.stubs.ClientAccountRequestDtoStubs.ACCOUNT_NOT_IN_MG_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountRequestDtoStubs.ANOTHER_USER_IN_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountRequestDtoStubs.CARD_NOT_IN_MG_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.ACCOUNT_IN_MG_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.CARD_IN_MG_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.USER_IN_MG_BASE_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ClientAccountControllerIntegrationTest extends MongoIntegrationTestConfig {

    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    private ClientAccount testClientAccountInBase;

    @BeforeEach
    void setUp() {
        clientAccountRepository.deleteAll();
        testClientAccountInBase = ClientAccountStubs.createClientAccountInBaseStubs();
        clientAccountRepository.save(testClientAccountInBase);
    }

    @Test
    void getClientAccountsTest() throws Exception {
        mockMvc.perform(get("/clientAccounts/" + testClientAccountInBase.getUserId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(USER_IN_MG_BASE_ID))
                .andExpect(jsonPath("$.accounts").isArray())
                .andExpect(jsonPath("$.accounts", hasSize(1)))
                .andExpect(jsonPath("$.accounts[0].id").value(ACCOUNT_IN_MG_BASE_ID))
                .andExpect(jsonPath("$.accounts[0].cards").isArray())
                .andExpect(jsonPath("$.accounts[0].cards", hasSize(3)))
                .andExpect(jsonPath("$.accounts[0].cards[0].id").value(CARD_IN_MG_BASE_ID));
    }

    @Test
    void saveClientAccountTest() throws Exception {
        ClientAccountCreateRequestDto testClientAccountDtoNotInBase = ClientAccountRequestDtoStubs.createClientAccountCreateRequestDtoStubs();
        mockMvc.perform(post("/clientAccounts")
                        .content(objectMapper.writeValueAsString(testClientAccountDtoNotInBase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(ANOTHER_USER_IN_BASE_ID))
                .andExpect(jsonPath("$.accounts").isArray())
                .andExpect(jsonPath("$.accounts", hasSize(1)))
                .andExpect(jsonPath("$.accounts[0].id").value(ACCOUNT_NOT_IN_MG_BASE_ID))
                .andExpect(jsonPath("$.accounts[0].cards").isArray())
                .andExpect(jsonPath("$.accounts[0].cards", hasSize(1)))
                .andExpect(jsonPath("$.accounts[0].cards[0].id").value(CARD_NOT_IN_MG_BASE_ID));
    }

    @Test
    void updateClientWithAccountsTest() throws Exception {
        AccountCreateRequestDto testAccountDto = ClientAccountRequestDtoStubs.createAccountRequestDtoStubs();
        mockMvc.perform(patch("/clientAccounts/" + USER_IN_MG_BASE_ID + "/accounts")
                        .content(objectMapper.writeValueAsString(List.of(testAccountDto)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Данные клиента успешно обновлены для пользователя с id %s".formatted(USER_IN_MG_BASE_ID)));
    }

    @Test
    void updateClientWithCardsTest() throws Exception {
        List<CardCreateRequestDto> testCardsRequest = ClientAccountRequestDtoStubs.createCardRequestDtoListStubs();
        mockMvc.perform(patch("/clientAccounts/" + testClientAccountInBase.getUserId() + "/" + testClientAccountInBase.getAccounts().get(0).getId() + "/cards")
                        .content(objectMapper.writeValueAsString(testCardsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Данные успешно обновлены для пользователя с id %s и аккаунт id %s".formatted(USER_IN_MG_BASE_ID, ACCOUNT_IN_MG_BASE_ID)));
    }

    @Test
    void getClientCardsWithFilterTest() throws Exception {
        boolean testFilter = true;
        mockMvc.perform(get("/clientAccounts/" + testClientAccountInBase.getUserId() + "/cards")
                        .param("showSoonExpiring", Boolean.toString(testFilter))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(CARD_IN_MG_BASE_ID));
    }

}
