package com.example.jayraj.transactiontest;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
@EnableWebFlux
@EnableTransactionManagement
@EnableKafka
public class TransactionTestApplication{
    public static void main(String[] args) {
        SpringApplication.run(TransactionTestApplication.class, args);
    }
}
