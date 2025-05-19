package com.minsk.frontendpracticeservice.kafka;

import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import com.minsk.frontendpracticeservice.stubs.KafkaStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@EmbeddedKafka(partitions = 3)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.group-id=requisites-create-group"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class KafkaProcessorTest {

    @Value("${spring.kafka.topics.requisites-service.requisites-create-topic-name}")
    private String requisitesCreateTopic;

    @Autowired
    private KafkaTemplate<String, RequisitesCreateResponseKafkaDto> kafkaTemplate;

    @SpyBean
    private KafkaProcessor kafkaProcessor;

    @Test
    void testHandleRequisitesCreate() throws ExecutionException, InterruptedException {

        RequisitesCreateResponseKafkaDto message = KafkaStubs.getRequisitesCreateResponseKafkaDtoStub();

        kafkaTemplate.send(requisitesCreateTopic, message).get();

        ArgumentCaptor<RequisitesCreateResponseKafkaDto> eventCaptor = ArgumentCaptor.forClass(RequisitesCreateResponseKafkaDto.class);
        verify(kafkaProcessor, timeout(5000)).handleRequisitesCreateResponse(eventCaptor.capture());

        assertEquals(message, eventCaptor.getValue());
    }
}
