package com.minsk.frontendpracticeservice.integration.service;

import com.minsk.frontendpracticeservice.config.MongoIntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.request.AccountUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.response.CardResponseDto;
import com.minsk.frontendpracticeservice.domain.response.ClientAccountResponseDto;
import com.minsk.frontendpracticeservice.exception.business.AccountNotFoundException;
import com.minsk.frontendpracticeservice.exception.business.NotAllowedToSaveClientAccountException;
import com.minsk.frontendpracticeservice.exception.business.UserNotFoundException;
import com.minsk.frontendpracticeservice.repository.ClientAccountRepository;
import com.minsk.frontendpracticeservice.repository.UserRepository;
import com.minsk.frontendpracticeservice.service.ClientAccountService;
import com.minsk.frontendpracticeservice.stubs.ClientAccountRequestDtoStubs;
import com.minsk.frontendpracticeservice.stubs.ClientAccountStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesStubs;
import com.minsk.frontendpracticeservice.stubs.UserStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.EXPIRATION_DATE;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.USER_IN_MG_BASE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ClientAccountServiceIntegrationTest extends MongoIntegrationTestConfig {

    @Autowired
    ClientAccountService clientAccountService;

    @Autowired
    ClientAccountRepository clientAccountRepository;

    @Autowired
    UserRepository userRepository;

    private ClientAccount testClientAccountInBase;

    @BeforeEach
    void setUp() {
        clientAccountRepository.deleteAll();
        testClientAccountInBase = ClientAccountStubs.createClientAccountInBaseStubs();
        User testUserInBase = UserStubs.createValidUserStubs();
        testUserInBase.setId(UUID.fromString(USER_IN_MG_BASE_ID));
        testUserInBase.setRequisites(RequisitesStubs.createValidRequisitesStubs());
        userRepository.save(testUserInBase);
        clientAccountRepository.save(testClientAccountInBase);
    }

    @Test
    void getClientAccountByUserIdPositiveTest() {
        ClientAccountResponseDto testResponseDto = clientAccountService.getClientAccountByUserId(testClientAccountInBase.getUserId());

        assertThat(testResponseDto).isNotNull();
        assertThat(testResponseDto.userId()).isEqualTo(testClientAccountInBase.getUserId());
        assertThat(testResponseDto.accounts()).isNotEmpty();
        assertThat(testResponseDto.accounts()).hasSize(1);
        assertThat(testResponseDto.accounts().get(0).cards()).isNotEmpty();
        assertThat(testResponseDto.accounts().get(0).cards()).hasSize(3);
    }

    @Test
    void getClientAccountByUserIdThrowsUserNotFoundExceptionTest() {
        String userIdNotInBase = UUID.randomUUID().toString();
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            clientAccountService.getClientAccountByUserId(userIdNotInBase);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Пользователь с таким id %s не найден", userIdNotInBase));
    }

    @Test
    void saveClientAccountPositiveTest() {
        ClientAccountCreateRequestDto clientAccountDtoNotInBase = ClientAccountRequestDtoStubs.createClientAccountCreateRequestDtoStubs();
        ClientAccountResponseDto testClientAccountResponseDto = clientAccountService.saveClientAccount(clientAccountDtoNotInBase);
        assertThat(testClientAccountResponseDto).isNotNull();
        assertThat(testClientAccountResponseDto.userId()).isEqualTo(clientAccountDtoNotInBase.userId());
        assertThat(testClientAccountResponseDto.accounts()).isNotEmpty();
        assertThat(testClientAccountResponseDto.accounts()).hasSize(1);
        assertThat(testClientAccountResponseDto.accounts().get(0).cards()).isNotEmpty();
        assertThat(testClientAccountResponseDto.accounts().get(0).cards()).hasSize(1);
    }

    @Test
    void saveClientAccountThrowsUserNotFoundExceptionTest() {
        String userIdNotInBase = UUID.randomUUID().toString();
        ClientAccountCreateRequestDto clientAccountDtoNotInBase = ClientAccountRequestDtoStubs.createClientAccountCreateRequestDtoWithInvalidUserStubs(userIdNotInBase);
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            clientAccountService.saveClientAccount(clientAccountDtoNotInBase);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Пользователь с таким id %s не найден", userIdNotInBase));
    }

    @Test
    void saveClientAccountNotAllowedToSaveClientAccountExceptionTest() {
        String userIdInBase = testClientAccountInBase.getUserId();
        ClientAccountCreateRequestDto clientAccountDtoAlreadyInBase = ClientAccountRequestDtoStubs.createClientAccountCreateRequestDtoWithInvalidUserStubs(userIdInBase);

        Exception exception = assertThrows(NotAllowedToSaveClientAccountException.class, () -> {
            clientAccountService.saveClientAccount(clientAccountDtoAlreadyInBase);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Данные клиента с таким id пользователя %s уже существуют", userIdInBase));
    }

    @Test
    void updateClientAccountWithAccountsThrowsUserNotFoundExceptionTest() {
        String userIdNotInBase = UUID.randomUUID().toString();
        List<AccountUpdateRequestDto> accountUpdateRequestDtoList = ClientAccountRequestDtoStubs.createAccountRequestUpdateDtoStubs();
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            clientAccountService.updateClientAccountWithAccounts(userIdNotInBase, accountUpdateRequestDtoList);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Пользователь с таким id %s не найден", userIdNotInBase));
    }

    @Test
    void updateClientAccountWithCardsThrowsAccountNotFoundExceptionTest() {
        String userIdInBase = testClientAccountInBase.getUserId();
        String accountIdNotInBase = UUID.randomUUID().toString();
        List<CardUpdateRequestDto> cards = new ArrayList<>();
        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            clientAccountService.updateClientAccountWithCards(userIdInBase, accountIdNotInBase, cards);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("Счет клиента с таким id %s не найден", accountIdNotInBase));
    }

    @Test
    void getCardsByUserIdWithFilterPositiveTest() {
        List<CardResponseDto> testResponseDto = clientAccountService.getCardsByUserIdWithFilter(testClientAccountInBase.getUserId(), true);
        assertThat(testResponseDto).isNotNull();
        assertThat(testResponseDto).hasSize(1);
        assertThat(testResponseDto.get(0).expirationDate()).isEqualTo(EXPIRATION_DATE);
    }

}
