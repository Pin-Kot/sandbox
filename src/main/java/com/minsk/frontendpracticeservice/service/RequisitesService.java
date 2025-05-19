package com.minsk.frontendpracticeservice.service;

import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;

public interface RequisitesService {

    RequisitesCreateResponseDto createRequisites(RequisitesCreateRequestDto requisitesDto);

    RequisitesResponseDto readRequisites(String requisitesId);

    RequisitesResponseDto updateRequisites(RequisitesRequestUpdateDto requisitesDto);

    void deleteRequisites(String requisitesId);

}
