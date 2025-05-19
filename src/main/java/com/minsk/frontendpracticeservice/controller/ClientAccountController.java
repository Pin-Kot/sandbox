package com.minsk.frontendpracticeservice.controller;

import com.minsk.frontendpracticeservice.domain.request.AccountUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.response.CardResponseDto;
import com.minsk.frontendpracticeservice.domain.response.ClientAccountResponseDto;
import com.minsk.frontendpracticeservice.domain.response.SimpleMessage;
import com.minsk.frontendpracticeservice.service.ClientAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientAccounts")
@RequiredArgsConstructor
public class ClientAccountController {

    private final static String CLIENT_SUCCESSFULLY_UPDATED_WITH_ACCOUNTS_MESSAGE = "Данные клиента успешно обновлены для пользователя с id %s";
    private final static String CLIENT_SUCCESSFULLY_UPDATED_WITH_CARDS_MESSAGE = "Данные успешно обновлены для пользователя с id %s и аккаунт id %s";

    private final ClientAccountService clientAccountService;

    @PostMapping
    public ResponseEntity<ClientAccountResponseDto> saveClientAccount(@NotNull @Valid @RequestBody ClientAccountCreateRequestDto clientAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientAccountService.saveClientAccount(clientAccountDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ClientAccountResponseDto> getClientAccounts(@UUID @PathVariable String userId) {
        return ResponseEntity.ok(clientAccountService.getClientAccountByUserId(userId));
    }

    @PatchMapping("/{userId}/accounts")
    public ResponseEntity<SimpleMessage> updateClientWithAccounts(@PathVariable String userId, @NotNull @Valid @RequestBody List<AccountUpdateRequestDto> accountsUpdateDto) {
        clientAccountService.updateClientAccountWithAccounts(userId, accountsUpdateDto);
        return ResponseEntity.ok(new SimpleMessage(CLIENT_SUCCESSFULLY_UPDATED_WITH_ACCOUNTS_MESSAGE.formatted(userId)));
    }

    @PatchMapping("/{userId}/{accountId}/cards")
    public ResponseEntity<SimpleMessage> updateClientWithCards(@PathVariable String userId, @PathVariable String accountId, @NotNull @Valid @RequestBody List<CardUpdateRequestDto> cardsUpdateDto) {
        clientAccountService.updateClientAccountWithCards(userId, accountId, cardsUpdateDto);
        return ResponseEntity.ok(new SimpleMessage(CLIENT_SUCCESSFULLY_UPDATED_WITH_CARDS_MESSAGE.formatted(userId, accountId)));
    }

    @GetMapping("/{userId}/cards")
    public ResponseEntity<List<CardResponseDto>> getClientCardsWithFilter(@UUID @PathVariable String userId, Boolean showSoonExpiring) {
        return ResponseEntity.ok(clientAccountService.getCardsByUserIdWithFilter(userId, showSoonExpiring));
    }

}
