package com.minsk.frontendpracticeservice.domain.response;

import lombok.Builder;

import java.util.UUID;

public record RequisitesCreateResponseDto(UUID id, String accountNumber, String inn) {

    @Builder
    public RequisitesCreateResponseDto {
    }

}
