package com.minsk.frontendpracticeservice.kafka;

import com.minsk.frontendpracticeservice.config.KafkaConfig;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

    private final KafkaConfig kafkaConfig;

    public void sendKafkaRequisitesCreateMessage(RequisitesCreateResponseKafkaDto message) {
        log.info("Отправка сообщения в Кафка {}", message);
        try {
            SendResult<String, RequisitesCreateResponseKafkaDto> result = kafkaConfig.createResponseKafkaTemplate().send(kafkaConfig.requisitesCreateTopicName, message)
                    .get(10_000L, TimeUnit.MILLISECONDS);
            log.info("Сообщение [{}] отправлено успешно", message);
            log.info("Топик: [{}]", result.getRecordMetadata().topic());
            log.info("Партиция: [{}]", result.getRecordMetadata().partition());
            log.info("Смещение: [{}]", result.getRecordMetadata().offset());
        } catch (ExecutionException | TimeoutException e) {
            log.error("Ошибка отправки сообщения: [{}]", e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
