package com.minsk.frontendpracticeservice.domain.request;

import com.minsk.frontendpracticeservice.validation.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CardCreateRequestDto(
        @NotBlank
        @Pattern(regexp = Utils.UUID_REGEX, message = "ID должен соответствовать формату UUID")
        String id,
        @NotBlank
        @Pattern(regexp = Utils.CARD_REGEX, message = "номер карты должен состоять из 16 цифр")
        String number,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate expirationDate,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate issueDate,
        @NotBlank
        String type) {
}
