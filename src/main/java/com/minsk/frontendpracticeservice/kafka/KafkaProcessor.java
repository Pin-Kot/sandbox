package com.minsk.frontendpracticeservice.kafka;

import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@KafkaListener(topics = "${spring.kafka.topics.requisites-service.requisites-create-topic-name}",
        groupId = "${spring.kafka.consumer.group-id}")
public class KafkaProcessor {

    @KafkaHandler
    public void handleRequisitesCreateResponse(RequisitesCreateResponseKafkaDto requisitesResponse) {
        log.info("Созданные реквизиты [{}] получены", requisitesResponse);
    }

    @KafkaHandler
    public void handleRequisitesCreateResponse(String message) {
        log.info(message);
    }

}
