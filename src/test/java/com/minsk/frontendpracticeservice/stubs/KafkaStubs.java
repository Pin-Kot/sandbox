package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;

import java.util.UUID;

public class KafkaStubs {

    public static RequisitesCreateResponseKafkaDto getRequisitesCreateResponseKafkaDtoStub() {
        return new RequisitesCreateResponseKafkaDto(
                UUID.fromString("526e4df4-1c09-42bd-b221-0640f5257d76"),
                "40567890012345678120",
                "044525225",
                "40702810000000000006",
                "123456789010",
                "567890125",
                "18210101010010000106"
        );
    }
}
