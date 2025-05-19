package com.minsk.frontendpracticeservice.domain.request;

import com.minsk.frontendpracticeservice.validation.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AccountCreateRequestDto(
        @NotBlank
        @Pattern(regexp = Utils.UUID_REGEX, message = "ID должен соответствовать формату UUID")
        String id,
        @NotBlank
        @Pattern(regexp = Utils.ACCOUNT_NUMBER_REGEX, message = "Расчетный счет должен состоять из 20 цифр")
        String number,
        @NotNull
        @PositiveOrZero
        BigDecimal balance,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate openDate,
        @NotNull
        List<CardCreateRequestDto> cards) {
}
