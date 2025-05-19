package com.minsk.frontendpracticeservice.service.impl;

import com.minsk.frontendpracticeservice.domain.entity.Account;
import com.minsk.frontendpracticeservice.domain.entity.Card;
import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import com.minsk.frontendpracticeservice.domain.mapper.AccountMapper;
import com.minsk.frontendpracticeservice.domain.mapper.CardMapper;
import com.minsk.frontendpracticeservice.domain.mapper.ClientAccountMapper;
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
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService {

    private static final String USER_WITH_ID_NOT_FOUND_MESSAGE = "Пользователь с таким id %s не найден";
    private static final String ACCOUNT_WITH_ID_NOT_FOUND_MESSAGE = "Счет клиента с таким id %s не найден";
    private static final String CLIENT_WITH_USER_ID_EXISTS_MESSAGE = "Данные клиента с таким id пользователя %s уже существуют";

    private final ClientAccountRepository clientAccountRepository;

    private final UserRepository userRepository;

    private final ClientAccountMapper clientAccountMapper;

    private final AccountMapper accountMapper;

    private final CardMapper cardMapper;

    @Override
    @Transactional(readOnly = true)
    public ClientAccountResponseDto getClientAccountByUserId(String userId) {
        ClientAccount clientAccount = findClientAccountByUserId(userId);
        return clientAccountMapper.toClientAccountResponseDto(clientAccount);
    }

    @Override
    @Transactional
    public ClientAccountResponseDto saveClientAccount(ClientAccountCreateRequestDto clientAccountRequestDto) {
        if (!userRepository.existsById(UUID.fromString(clientAccountRequestDto.userId()))) {
            throw new UserNotFoundException(USER_WITH_ID_NOT_FOUND_MESSAGE.formatted(clientAccountRequestDto.userId()));
        }
        if (clientAccountRepository.existsByUserId(clientAccountRequestDto.userId())) {
            throw new NotAllowedToSaveClientAccountException(CLIENT_WITH_USER_ID_EXISTS_MESSAGE.formatted(clientAccountRequestDto.userId()));
        }
        ClientAccount clientAccount = clientAccountMapper.fromClientAccountRequestDto(clientAccountRequestDto);
        var clientAccountId = UUID.randomUUID().toString();
        clientAccount.setId(clientAccountId);
        var result = clientAccountRepository.save(clientAccount);
        return clientAccountMapper.toClientAccountResponseDto(result);
    }

    @Override
    @Transactional
    public void updateClientAccountWithAccounts(String userId, List<AccountUpdateRequestDto> accountsDtoList) {
        if (!clientAccountRepository.existsByUserId(userId)) {
            throw new UserNotFoundException(USER_WITH_ID_NOT_FOUND_MESSAGE.formatted(userId));
        }
        List<Account> accounts = accountsDtoList.stream()
                .map(accountMapper::fromAccountUpdateRequestDto)
                .toList();
        if (clientAccountRepository.updateClientAccountWithAccounts(userId, accounts) == 0) {
            throw new IllegalArgumentException("Неверные данные для сохранения");
        }
    }

    @Override
    @Transactional
    public void updateClientAccountWithCards(String userId, String accountId, List<CardUpdateRequestDto> cardsDtoList) {
        if (!isAccountExists(userId, accountId)) {
            throw new AccountNotFoundException(ACCOUNT_WITH_ID_NOT_FOUND_MESSAGE.formatted(accountId));
        }
        List<Card> cards = cardsDtoList.stream()
                .map(cardMapper::fromCardUpdateRequestDto)
                .toList();
        if (clientAccountRepository.updateClientAccountWithCards(userId, accountId, cards) == 0) {
            throw new IllegalArgumentException("Неверные данные для сохранения");
        }
    }

    @Override
    public List<CardResponseDto> getCardsByUserIdWithFilter(String userId, Boolean showSoonExpiring) {
        if (!clientAccountRepository.existsByUserId(userId)) {
            throw new UserNotFoundException(USER_WITH_ID_NOT_FOUND_MESSAGE.formatted(userId));
        }

        return clientAccountRepository.findCardsByUserId(userId).stream()
                .filter(getCardFilterPredicate(showSoonExpiring))
                .map(cardMapper::toCardResponseDto)
                .toList();
    }

    private @NotNull Predicate<Card> getCardFilterPredicate(Boolean showSoonExpiring) {
        return card -> showSoonExpiring == null
                || !showSoonExpiring
                || card.getExpirationDate().isBefore(LocalDate.now().plusDays(30));
    }

    private ClientAccount findClientAccountByUserId(String userId) {
        return clientAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_ID_NOT_FOUND_MESSAGE.formatted(userId)));
    }

    private boolean isAccountExists(String userId, String accountId) {
        return findClientAccountByUserId(userId).getAccounts().stream()
                .anyMatch(a -> a.getId().equals(accountId));
    }

}
