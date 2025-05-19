package com.minsk.frontendpracticeservice.domain.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "@class",
        defaultImpl = RequisitesResponseDto.class
)
public record RequisitesResponseDto(UUID id,
                                    String accountNumber,
                                    String bic,
                                    String correspondentAccount,
                                    String inn,
                                    String kpp,
                                    String kbk) {

}
