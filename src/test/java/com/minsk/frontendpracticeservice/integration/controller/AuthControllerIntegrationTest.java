package com.minsk.frontendpracticeservice.integration.controller;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.request.AuthRequest;
import com.minsk.frontendpracticeservice.domain.request.RefreshJwtRequest;
import com.minsk.frontendpracticeservice.stubs.UserStubs;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = UserStubs.createValidUserStubs();
    }

    @Test
    void authAndGetTokenThrowsNotFoundErrorTest() throws Exception {
        String incorrectPassword = "password";
        AuthRequest authRequest = new AuthRequest(validUser.getLogin(), incorrectPassword);
        mockMvc.perform(post("/frontend-practice/auth/login")
                        .content(objectMapper.writeValueAsString(authRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Некорректный пароль"));
    }

    @Test
    void getNewAccessTokenThrowsNotFoundErrorTest() throws Exception {

        RefreshJwtRequest refreshJwtRequest = new RefreshJwtRequest();
        refreshJwtRequest.setRefreshToken("incorrectToken");
        mockMvc.perform(post("/frontend-practice/auth/refresh")
                        .content(objectMapper.writeValueAsString(refreshJwtRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректный токен авторизации"));
    }

}
