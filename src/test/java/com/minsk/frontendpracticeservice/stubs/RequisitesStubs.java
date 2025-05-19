package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;

import java.util.UUID;

public class RequisitesStubs {

    public static final UUID REQUISITES_ID = UUID.fromString("b2d83dcb-6472-4df0-9188-af916dec7f35");
    public static final String REQUISITES_ACCOUNT_NUMBER = "40567890012345678123";
    public static final String REQUISITES_KBK = "18210101010010000101";
    public static final String REQUISITES_INN = "123456789012";

    public static Requisites createValidRequisitesStubs() {
        return Requisites.builder()
                .id(REQUISITES_ID)
                .accountNumber(REQUISITES_ACCOUNT_NUMBER)
                .bic("044525225")
                .correspondentAccount("40702810000000000001")
                .inn(REQUISITES_INN)
                .kpp("123456789")
                .kbk(REQUISITES_KBK)
                .build();
    }

    public static Requisites createNotInBaseRequisitesStubs() {
        return Requisites.builder()
                .accountNumber("40567890012345678119")
                .bic("044525225")
                .correspondentAccount("40702810000000000016")
                .inn("123456789110")
                .kpp("567890125")
                .kbk("18210101010010000106")
                .build();
    }

    public static Requisites createAnotherValidRequisitesStubs() {
        return Requisites.builder()
                .id(UUID.fromString("c2c3574e-fa5b-4336-a175-ada64db5279f"))
                .accountNumber("40567890012345678124")
                .bic("044525226")
                .correspondentAccount("40702810000000000002")
                .inn("234567890123")
                .kpp("234567890")
                .kbk("18210101010010000102")
                .build();
    }

    public static Requisites createUpdatedRequisitesStubs() {
        Requisites requisites = createValidRequisitesStubs();
        requisites.setKpp("123456000");
        requisites.setKbk("18290909090090000000");
        requisites.setCorrespondentAccount("40702810000000000000");
        return requisites;
    }

}
