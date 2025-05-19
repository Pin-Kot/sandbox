package com.minsk.frontendpracticeservice.integration.service;

import com.minsk.frontendpracticeservice.config.IntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;
import com.minsk.frontendpracticeservice.exception.business.NotAllowedToCreateRequisitesException;
import com.minsk.frontendpracticeservice.exception.business.RequisitesNotFoundException;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import com.minsk.frontendpracticeservice.stubs.RequisitesRequestDtoStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesResponseDtoStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ImportTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RequisitesServiceIntegrationTest extends IntegrationTestConfig {

    @Autowired
    private RequisitesService requisitesService;

    private Requisites requisitesNotInBase;
    private Requisites requisitesInBase;
    private Requisites updatedRequisites;

    @BeforeEach
    void setUp() {
        requisitesNotInBase = RequisitesStubs.createNotInBaseRequisitesStubs();
        requisitesInBase = RequisitesStubs.createValidRequisitesStubs();
        updatedRequisites = RequisitesStubs.createUpdatedRequisitesStubs();
    }

    @Test
    void createRequisitesPositiveTest() {

        RequisitesCreateRequestDto requisitesNotInBaseDto = RequisitesCreateRequestDto.builder()
                .accountNumber(requisitesNotInBase.getAccountNumber())
                .bic(requisitesNotInBase.getBic())
                .correspondentAccount(requisitesNotInBase.getCorrespondentAccount())
                .inn(requisitesNotInBase.getInn())
                .kpp(requisitesNotInBase.getKpp())
                .kbk(requisitesNotInBase.getKbk())
                .build();

        RequisitesCreateResponseDto testResponseDto = requisitesService.createRequisites(requisitesNotInBaseDto);

        assertThat(testResponseDto).isNotNull();
        assertEquals(requisitesNotInBaseDto.getAccountNumber(), testResponseDto.accountNumber());
        assertEquals(requisitesNotInBaseDto.getInn(), testResponseDto.inn());
    }

    @Test
    void createRequisitesThrowNotAllowedToCreateRequisitesTest() {

        RequisitesCreateRequestDto requisitesInBaseDto = RequisitesCreateRequestDto.builder()
                .accountNumber(requisitesInBase.getAccountNumber())
                .bic(requisitesInBase.getBic())
                .correspondentAccount(requisitesInBase.getCorrespondentAccount())
                .inn(requisitesInBase.getInn())
                .kpp(requisitesInBase.getKpp())
                .kbk(requisitesInBase.getKbk())
                .build();
        Exception exception = assertThrows(NotAllowedToCreateRequisitesException.class, () -> {
            requisitesService.createRequisites(requisitesInBaseDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Реквизиты с таким счетом уже существуют");
    }


    @Test
    void readRequisitesPositiveTest() {
        requisitesInBase = RequisitesStubs.createAnotherValidRequisitesStubs();
        RequisitesResponseDto responseRequisitesDtoInBase = RequisitesResponseDtoStubs.createRequisitesResponseDtoStubs(requisitesInBase);

        RequisitesResponseDto testResponseDto = requisitesService.readRequisites(requisitesInBase.getId().toString());
        assertThat(testResponseDto).isNotNull();
        assertEquals(responseRequisitesDtoInBase, testResponseDto);
    }

    @Test
    void readRequisitesThrowRequisitesNotFoundExceptionTest() {
        String idNotInBase = UUID.randomUUID().toString();
        Exception exception = assertThrows(RequisitesNotFoundException.class, () -> {
            requisitesService.readRequisites(idNotInBase);
        });

        assertThat(exception.getMessage()).isEqualTo("Реквизиты с таким id %s не найдены".formatted(idNotInBase));
    }

    @Test
    @Rollback
    void updateRequisitesPositiveTest() {
        RequisitesRequestUpdateDto validRequisitesRequestUpdateDto = RequisitesRequestDtoStubs.createRequisitesRequestUpdateDtoStubs(updatedRequisites);
        RequisitesResponseDto responseRequisitesDtoInBase = RequisitesResponseDtoStubs.createRequisitesResponseDtoStubs(updatedRequisites);

        RequisitesResponseDto testResponseDto = requisitesService.updateRequisites(validRequisitesRequestUpdateDto);
        assertThat(testResponseDto).isNotNull();
        assertEquals(responseRequisitesDtoInBase, testResponseDto);
    }

    @Test
    void updateThrowRequisitesNotFoundExceptionTest() {

        requisitesNotInBase.setId(UUID.randomUUID());
        RequisitesRequestUpdateDto validRequisitesRequestUpdateDto = RequisitesRequestDtoStubs.createRequisitesRequestUpdateDtoStubs(requisitesNotInBase);

        Exception exception = assertThrows(RequisitesNotFoundException.class, () -> {
            requisitesService.updateRequisites(validRequisitesRequestUpdateDto);
        });
        assertThat(exception.getMessage()).isEqualTo("Реквизиты с таким id %s не найдены".formatted(requisitesNotInBase.getId().toString()));
    }

    @Test
    void deleteRequisitesPositiveTest() {

        requisitesService.deleteRequisites(requisitesInBase.getId().toString());
        assertThrows(RequisitesNotFoundException.class,
                () -> requisitesService.readRequisites(requisitesInBase.getId().toString()));
    }

}
