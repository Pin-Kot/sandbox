package com.minsk.frontendpracticeservice.config;

import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseKafkaDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topics.replication-factor}")
    private Integer topicReplicationFactor;

    @Value("${spring.kafka.topics.partitions}")
    private Integer topicPartitions;

    @Value("${spring.kafka.topics.requisites-service.requisites-create-topic-name}")
    public String requisitesCreateTopicName;

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public NewTopic requisitesCreateTopic() {
        return TopicBuilder
                .name(requisitesCreateTopicName)
                .replicas(topicReplicationFactor)
                .partitions(topicPartitions)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    public ProducerFactory<String, RequisitesCreateResponseKafkaDto> requisitesCreateResponseProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ProducerConfig.ACKS_CONFIG, "all",
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                        ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 21100,
                        ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 9900,
                        ProducerConfig.LINGER_MS_CONFIG, 0,
                        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
                        ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 4

                ),
                new StringSerializer(), new JsonSerializer<>()
        );
    }

    @Bean
    public KafkaTemplate<String, RequisitesCreateResponseKafkaDto> createResponseKafkaTemplate() {
        return new KafkaTemplate<>(requisitesCreateResponseProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, RequisitesCreateResponseKafkaDto> createResponseKafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest",
                        ConsumerConfig.GROUP_ID_CONFIG, "requisites-create-group"
                ), new StringDeserializer(), new JsonDeserializer<>(RequisitesCreateResponseKafkaDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RequisitesCreateResponseKafkaDto> createKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, RequisitesCreateResponseKafkaDto>();
        factory.setConsumerFactory(createResponseKafkaConsumerFactory());
        return factory;
    }

}
