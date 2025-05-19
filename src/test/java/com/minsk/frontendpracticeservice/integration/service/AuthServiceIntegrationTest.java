package com.minsk.frontendpracticeservice.integration.service;

import com.minsk.frontendpracticeservice.config.IntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.request.AuthRequest;
import com.minsk.frontendpracticeservice.exception.auth.AuthException;
import com.minsk.frontendpracticeservice.exception.auth.JwtException;
import com.minsk.frontendpracticeservice.service.AuthService;
import com.minsk.frontendpracticeservice.stubs.UserStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AuthServiceIntegrationTest extends IntegrationTestConfig {

    @Autowired
    AuthService authService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = UserStubs.createValidUserStubs();
    }

    @Test
    void authAndGetTokenThrowsAuthExceptionTest() {
        String incorrectPassword = "password";
        AuthRequest authRequest = new AuthRequest(validUser.getLogin(), incorrectPassword);
        Exception exception = assertThrows(AuthException.class, () -> {
            authService.authAndGetToken(authRequest);
        });
        assertThat(exception.getMessage()).isEqualTo("Некорректный пароль");
    }

    @Test
    void getAccessTokenThrowsJwtExceptionTest() {
        String incorrectTokenRequest = "incorrectToken";
        Exception exception = assertThrows(JwtException.class, () -> {
            authService.getAccessToken(incorrectTokenRequest);
        });
        assertThat(exception.getMessage()).isEqualTo("Некорректный токен авторизации");
    }

}
