package com.minsk.frontendpracticeservice.domain.response;

import java.util.UUID;

public record RequisitesCreateResponseKafkaDto(UUID id,
                                               String accountNumber,
                                               String bic,
                                               String correspondentAccount,
                                               String inn, String kpp,
                                               String kbk) {

}
