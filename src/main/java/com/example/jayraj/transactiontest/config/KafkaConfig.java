package com.example.jayraj.transactiontest.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, String>(SenderOptions.create(props));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate(ReceiverOptions<String, String> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, String>(kafkaReceiverOptions);
    }

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
        ReceiverOptions<String, String> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList("mytopic"));
    }

    @Bean
    public KafkaSender<String, String> kadkaSender(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx");
        SenderOptions<String, String> senderOptions = SenderOptions.create(props);

        return KafkaSender.create(senderOptions);
    }
}