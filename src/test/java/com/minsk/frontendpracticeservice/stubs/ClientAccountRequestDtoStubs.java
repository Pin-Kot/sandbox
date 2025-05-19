package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.request.AccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.AccountUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.CardUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.ACCOUNT_IN_MG_BASE_ID;
import static com.minsk.frontendpracticeservice.stubs.ClientAccountStubs.CARD_IN_MG_BASE_ID;

public class ClientAccountRequestDtoStubs {

    public static final String CARD_NOT_IN_MG_BASE_ID = "a1b2c3d4-e5f6-7890-1234-567890ghijkl";
    public static final String ACCOUNT_NOT_IN_MG_BASE_ID = "29c5eec1-0ef2-40f7-83c1-ea2de9dc1111";
    public static final String ANOTHER_USER_IN_BASE_ID = "9729d5b5-04c4-473e-8a35-70d9464c6acd";

    public static ClientAccountCreateRequestDto createClientAccountCreateRequestDtoStubs() {

        CardCreateRequestDto card = new CardCreateRequestDto(CARD_NOT_IN_MG_BASE_ID,
                "1111222233335555",
                LocalDate.of(2027, 5, 30),
                LocalDate.of(2025, 5, 1),
                "DEBIT");


        AccountCreateRequestDto account = new AccountCreateRequestDto(ACCOUNT_NOT_IN_MG_BASE_ID,
                "40567890012345678999",
                BigDecimal.valueOf(0L),
                LocalDate.of(2025, 1, 1),
                List.of(card));

        return new ClientAccountCreateRequestDto(ANOTHER_USER_IN_BASE_ID, List.of(account));
    }

    public static ClientAccountCreateRequestDto createClientAccountCreateRequestDtoWithInvalidUserStubs(String notValidUserId) {

        CardCreateRequestDto card = new CardCreateRequestDto(CARD_NOT_IN_MG_BASE_ID,
                "1111222233335555",
                LocalDate.of(2027, 5, 30),
                LocalDate.of(2025, 5, 1),
                "DEBIT");


        AccountCreateRequestDto account = new AccountCreateRequestDto(ACCOUNT_NOT_IN_MG_BASE_ID,
                "40567890012345678999",
                BigDecimal.valueOf(0L),
                LocalDate.of(2025, 1, 1),
                List.of(card));

        return new ClientAccountCreateRequestDto(notValidUserId, List.of(account));
    }

    public static List<AccountUpdateRequestDto> createAccountRequestUpdateDtoStubs() {

        CardUpdateRequestDto card = new CardUpdateRequestDto(CARD_NOT_IN_MG_BASE_ID,
                "1111222233335555",
                LocalDate.of(2027, 5, 30),
                LocalDate.of(2025, 5, 1),
                "DEBIT");


        return List.of(new AccountUpdateRequestDto(ACCOUNT_NOT_IN_MG_BASE_ID,
                "40567890012345678999",
                BigDecimal.valueOf(0L),
                LocalDate.of(2025, 1, 1),
                List.of(card)));
    }

    public static List<AccountUpdateRequestDto> createAccountRequestUpdateExistsDtoStubs() {

        CardUpdateRequestDto card = new CardUpdateRequestDto(CARD_IN_MG_BASE_ID,
                "1111222233334444",
                LocalDate.of(2025, 5, 30),
                LocalDate.of(2023, 5, 10),
                "DEBIT");

        return List.of(new AccountUpdateRequestDto(ACCOUNT_IN_MG_BASE_ID,
                "40567890012345678322",
                BigDecimal.valueOf(0L),
                LocalDate.of(2020, 1, 1),
                List.of(card)));
    }

    public static AccountCreateRequestDto createAccountRequestDtoStubs() {
        CardCreateRequestDto card = new CardCreateRequestDto(CARD_NOT_IN_MG_BASE_ID,
                "1111222233335555",
                LocalDate.of(2027, 5, 30),
                LocalDate.of(2025, 5, 1),
                "DEBIT");
        return new AccountCreateRequestDto(ACCOUNT_NOT_IN_MG_BASE_ID,
                "40567890012345678999",
                BigDecimal.valueOf(0L),
                LocalDate.of(2025, 1, 1),
                List.of(card));
    }

    public static List<CardCreateRequestDto> createCardRequestDtoListStubs() {
        CardCreateRequestDto card1 = new CardCreateRequestDto("b2c3d4e5-f678-9012-3456-7890abcdef01",
                "5555666677778888",
                LocalDate.of(2025, 5, 20),
                LocalDate.of(2023, 5, 1),
                "DEBIT");
        CardCreateRequestDto card2 = new CardCreateRequestDto("c3d4e5f6-7890-1234-5678-90abcdef0123",
                "9999000011112222",
                LocalDate.of(2026, 10, 13),
                LocalDate.of(2023, 5, 2),
                "DEBIT");
        return List.of(card1, card2);
    }

}
