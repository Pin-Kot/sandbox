package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.entity.Account;
import com.minsk.frontendpracticeservice.domain.entity.Card;
import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import com.minsk.frontendpracticeservice.domain.enums.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ClientAccountStubs {

    public static final String CARD_IN_MG_BASE_ID = "a1b2c3d4-e5f6-7890-1234-567890abcdef";
    public static final String ACCOUNT_IN_MG_BASE_ID = "29c5eec1-0ef2-40f7-83c1-ea2de9d7189c";
    public static final String USER_IN_MG_BASE_ID = "fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8";
    public static final LocalDate EXPIRATION_DATE = LocalDate.of(2025, 5, 30);

    public static ClientAccount createClientAccountInBaseStubs() {

        Card card1 = Card.builder()
                .id(CARD_IN_MG_BASE_ID)
                .number("1111222233334444")
                .expirationDate(EXPIRATION_DATE)
                .issueDate(LocalDate.of(2023, 5, 10))
                .type(CardType.DEBIT)
                .build();
        Card card2 = Card.builder()
                .id("c3d4e5f6-7890-1234-5678-90abcdef1235")
                .number("2111222233334445")
                .expirationDate(LocalDate.of(2026, 6, 30))
                .issueDate(LocalDate.of(2023, 6, 1))
                .type(CardType.CREDIT)
                .build();
        Card card3 = Card.builder()
                .id("c3d4e5f6-7890-1234-5678-90abcdef1236")
                .number("2111222233334446")
                .expirationDate(LocalDate.of(2027, 8, 31))
                .issueDate(LocalDate.of(2024, 8, 1))
                .type(CardType.DEBIT)
                .build();

        Account account = Account.builder()
                .id(ACCOUNT_IN_MG_BASE_ID)
                .number("40567890012345678322")
                .balance(BigDecimal.valueOf(10L))
                .openDate(LocalDate.of(2020, 1, 1))
                .cards(List.of(card1, card2, card3))
                .build();

        return ClientAccount.builder()
                .userId(USER_IN_MG_BASE_ID)
                .accounts(List.of(account))
                .build();
    }

}
