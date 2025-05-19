package com.minsk.frontendpracticeservice.domain.mapper;

import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import com.minsk.frontendpracticeservice.domain.request.ClientAccountCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.response.ClientAccountResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientAccountMapper {

    ClientAccountResponseDto toClientAccountResponseDto(ClientAccount clientAccount);

    ClientAccount fromClientAccountRequestDto(ClientAccountCreateRequestDto clientAccountRequestDto);

}
