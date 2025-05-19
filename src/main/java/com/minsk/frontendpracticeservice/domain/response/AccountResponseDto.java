package com.minsk.frontendpracticeservice.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AccountResponseDto(String id,
                                 String number,
                                 BigDecimal balance,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                 LocalDate openDate,
                                 List<CardResponseDto> cards) {
}