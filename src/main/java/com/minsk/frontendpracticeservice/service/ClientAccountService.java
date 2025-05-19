package com.minsk.frontendpracticeservice.service;

import com.minsk.frontendpracticeservice.domain.request.AccountUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.response.CardResponseDto;
import com.minsk.frontendpracticeservice.domain.response.ClientAccountResponseDto;

import java.util.List;

public interface ClientAccountService {

    ClientAccountResponseDto saveClientAccount(ClientAccountCreateRequestDto clientAccountRequestDto);

    ClientAccountResponseDto getClientAccountByUserId(String userId);

    void updateClientAccountWithAccounts(String userId, List<AccountUpdateRequestDto> accountsDtoList);

    void updateClientAccountWithCards(String userId, String accountId, List<CardUpdateRequestDto> cardsDtoList);

    List<CardResponseDto> getCardsByUserIdWithFilter(String userId, Boolean showSoonExpiring);

}
