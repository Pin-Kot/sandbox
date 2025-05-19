package com.minsk.frontendpracticeservice.kafka;

import com.minsk.frontendpracticeservice.config.KafkaConfig;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import com.minsk.frontendpracticeservice.stubs.KafkaStubs;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@EmbeddedKafka(partitions = 3, controlledShutdown = true)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.group-id=requisites-create-group"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class KafkaSenderTest {

    @Autowired
    KafkaConfig kafkaConfig;

    private static final String REQUISITES_CREATE_TOPIC = "requisites-create-topic";

    private final RequisitesCreateResponseKafkaDto messageToSend = KafkaStubs.getRequisitesCreateResponseKafkaDtoStub();


    @Test
    void testTopicCreation() {
        try (
                var consumer = kafkaConfig.createKafkaListenerContainerFactory()
                        .getConsumerFactory().createConsumer();
                ) {
            Assertions.assertThat(consumer.listTopics())
                    .isNotNull().isNotEmpty();
        }
    }

    @Test
    void testProducerSendRequisitesCreateMessage() throws ExecutionException, InterruptedException {
        var template = kafkaConfig.createResponseKafkaTemplate();
        template.send(REQUISITES_CREATE_TOPIC, messageToSend).get();
        var consumer = kafkaConfig.createKafkaListenerContainerFactory().getConsumerFactory().createConsumer();
        consumer.subscribe(List.of(REQUISITES_CREATE_TOPIC));
        var records = consumer.poll(Duration.ofMillis(30_000L));

        Assertions.assertThat(records).isNotNull().isNotEmpty();
        Assertions.assertThat(records.iterator().next().value()).isNotNull();
    }

}
