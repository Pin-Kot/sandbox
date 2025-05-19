package com.minsk.frontendpracticeservice.domain.mapper;

import com.minsk.frontendpracticeservice.domain.entity.Account;
import com.minsk.frontendpracticeservice.domain.request.AccountUpdateRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    Account fromAccountUpdateRequestDto(AccountUpdateRequestDto accountUpdateRequestDto);

}
