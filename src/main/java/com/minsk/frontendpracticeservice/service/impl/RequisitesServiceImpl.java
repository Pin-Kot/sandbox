package com.minsk.frontendpracticeservice.service.impl;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.mapper.RequisitesMapper;
import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;
import com.minsk.frontendpracticeservice.exception.business.NotAllowedToCreateRequisitesException;
import com.minsk.frontendpracticeservice.exception.business.RequisitesNotFoundException;
import com.minsk.frontendpracticeservice.kafka.KafkaSender;
import com.minsk.frontendpracticeservice.repository.RequisitesRepository;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequisitesServiceImpl implements RequisitesService {

    private static final String REQUISITES_WITH_ID_NOT_FOUND_MESSAGE = "Реквизиты с таким id %s не найдены";
    private static final String REQUISITES_WITH_ACCOUNT_NUMBER_EXISTS_MESSAGE = "Реквизиты с таким счетом уже существуют";

    private final RequisitesRepository requisitesRepository;

    private final RequisitesMapper requisitesMapper;

    private final KafkaSender kafkaSender;

    @Override
    @Transactional
    public RequisitesCreateResponseDto createRequisites(RequisitesCreateRequestDto requisitesDto) {
        if (requisitesRepository.existsByAccountNumber(requisitesDto.getAccountNumber())) {
            throw new NotAllowedToCreateRequisitesException(REQUISITES_WITH_ACCOUNT_NUMBER_EXISTS_MESSAGE);
        }
        Requisites requisites = requisitesRepository.save(requisitesMapper.fromCreateRequestToEntity(requisitesDto));
        kafkaSender.sendKafkaRequisitesCreateMessage(requisitesMapper.fromEntityToCreateResponseKafkaDto(requisites));
        return requisitesMapper.fromEntityToCreateResponseDto(requisites);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "requisites", key = "#requisitesId")
    public RequisitesResponseDto readRequisites(String requisitesId) {
        checkRequisitesId(requisitesId);
        Requisites requisites = requisitesRepository.findById(UUID.fromString(requisitesId))
                .orElseThrow(() -> new RequisitesNotFoundException(REQUISITES_WITH_ID_NOT_FOUND_MESSAGE.formatted(requisitesId)));
        return requisitesMapper.fromEntityToResponseDto(requisites);
    }

    @Override
    @Transactional
    @CachePut(value = "requisites", key = "#requisitesDto.requisitesId")
    public RequisitesResponseDto updateRequisites(RequisitesRequestUpdateDto requisitesDto) {
        Requisites requisites = requisitesRepository.findById(requisitesDto.getRequisitesId())
                .orElseThrow(() -> new RequisitesNotFoundException(REQUISITES_WITH_ID_NOT_FOUND_MESSAGE.formatted(requisitesDto.getRequisitesId())));
        requisitesMapper.updateEntityFromRequestDto(requisitesDto, requisites);
        requisitesRepository.saveAndFlush(requisites);
        return requisitesMapper.fromEntityToResponseDto(requisites);
    }

    @Override
    @Transactional
    @CacheEvict(value = "requisites", key = "#requisitesId")
    public void deleteRequisites(String requisitesId) {
        checkRequisitesId(requisitesId);
        requisitesRepository.deleteById(UUID.fromString(requisitesId));
    }

    private void checkRequisitesId(String requisitesId) {
        if (requisitesId == null || requisitesId.isEmpty()) {
            throw new IllegalArgumentException("Идентификационный номер реквизитов не может быть null");
        }
    }
}
