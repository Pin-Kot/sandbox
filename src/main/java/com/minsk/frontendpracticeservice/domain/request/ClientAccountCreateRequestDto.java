package com.minsk.frontendpracticeservice.domain.request;

import com.minsk.frontendpracticeservice.validation.Utils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ClientAccountCreateRequestDto(
        @NotBlank
        @Pattern(regexp = Utils.UUID_REGEX, message = "ID должен соответствовать формату UUID")
        String userId,
        @NotNull
        List<AccountCreateRequestDto> accounts) {
}
