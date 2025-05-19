package com.minsk.frontendpracticeservice.domain.mapper;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RequisitesMapper {

    Requisites fromCreateRequestToEntity(RequisitesCreateRequestDto requisitesCreateRequestDto);

    RequisitesCreateResponseDto fromEntityToCreateResponseDto(Requisites entity);

    RequisitesCreateResponseKafkaDto fromEntityToCreateResponseKafkaDto(Requisites entity);

    RequisitesResponseDto fromEntityToResponseDto(Requisites entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequestDto(RequisitesRequestUpdateDto requisitesRequestUpdateDto, @MappingTarget Requisites requisites);

}
