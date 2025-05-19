package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;

public class RequisitesResponseDtoStubs {

    public static RequisitesResponseDto createRequisitesResponseDtoStubs(Requisites requisites) {
        return new RequisitesResponseDto(
                requisites.getId(),
                requisites.getAccountNumber(),
                requisites.getBic(),
                requisites.getCorrespondentAccount(),
                requisites.getInn(),
                requisites.getKpp(),
                requisites.getKbk()
        );
    }
}
