package com.minsk.frontendpracticeservice.domain.mapper;

import com.minsk.frontendpracticeservice.domain.entity.Card;
import com.minsk.frontendpracticeservice.domain.request.CardUpdateRequestDto;
import com.minsk.frontendpracticeservice.domain.response.CardResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CardMapper {

    Card fromCardUpdateRequestDto(CardUpdateRequestDto cardUpdateRequestDto);

    CardResponseDto toCardResponseDto(Card card);

}
