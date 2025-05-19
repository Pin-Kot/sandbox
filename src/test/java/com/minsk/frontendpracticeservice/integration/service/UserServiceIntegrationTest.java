package com.minsk.frontendpracticeservice.integration.service;

import com.minsk.frontendpracticeservice.config.IntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.response.UserRequisitesProjection;
import com.minsk.frontendpracticeservice.exception.business.UserNotFoundException;
import com.minsk.frontendpracticeservice.repository.UserRepository;
import com.minsk.frontendpracticeservice.service.UserService;
import com.minsk.frontendpracticeservice.stubs.UserStubs;
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

import static com.minsk.frontendpracticeservice.stubs.RequisitesStubs.REQUISITES_ACCOUNT_NUMBER;
import static com.minsk.frontendpracticeservice.stubs.RequisitesStubs.REQUISITES_KBK;
import static com.minsk.frontendpracticeservice.stubs.UserStubs.USER_FIRST_NAME;
import static com.minsk.frontendpracticeservice.stubs.UserStubs.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserServiceIntegrationTest extends IntegrationTestConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = UserStubs.createValidUserStubs();
    }

    @Test
    void findByLoginPositiveTest() {
        User user = userService.findByLogin(validUser.getLogin());

        assertThat(user).isNotNull();
        assertThat(user.getLogin()).isEqualTo(validUser.getLogin());
    }

    @Test
    void findByLoginThrowUserNotFoundExceptionTest() {
        String invalidLogin = "NotInBD";

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findByLogin(invalidLogin);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Пользователь с таким логином %s не найден", invalidLogin));
    }

    @Test
    void findUserRequisitesProjectionByIdPositiveTest() {
        UserRequisitesProjection testProjection = userService.findUserRequisitesProjectionById(validUser.getId().toString());

        assertThat(testProjection).isNotNull();
        assertEquals(USER_FIRST_NAME, testProjection.getFirstName());
        assertEquals(REQUISITES_ACCOUNT_NUMBER, testProjection.getAccountNumber());
        assertEquals(REQUISITES_KBK, testProjection.getKbk());
    }

    @Test
    void findUserRequisitesProjectionByIdThrowUserNotFoundExceptionTest() {
        String invalidId = UUID.randomUUID().toString();
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserRequisitesProjectionById(invalidId);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Пользователь с таким id %s не найден", invalidId));
    }

    @Test
    void getUserRequisitesResponseTest() throws Exception {
        String validId = USER_ID.toString();
        mockMvc.perform(get("/users/requisites/" + validId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(USER_FIRST_NAME))
                .andExpect(jsonPath("$.accountNumber").value(REQUISITES_ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.kbk").value(REQUISITES_KBK));
    }

}
