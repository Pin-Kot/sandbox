package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;

public class RequisitesRequestDtoStubs {

    public static RequisitesRequestUpdateDto createRequisitesRequestUpdateDtoStubs(Requisites requisites) {
        return RequisitesRequestUpdateDto.builder()
                .requisitesId(requisites.getId())
                .bic(requisites.getBic())
                .correspondentAccount(requisites.getCorrespondentAccount())
                .kpp(requisites.getKpp())
                .kbk(requisites.getKbk())
                .build();
    }
}
