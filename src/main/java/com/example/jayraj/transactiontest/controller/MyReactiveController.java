package com.example.jayraj.transactiontest.controller;

import com.example.jayraj.transactiontest.model.Person;
import com.example.jayraj.transactiontest.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import sun.net.www.content.audio.x_aiff;

import java.util.UUID;

@Log4j2
@RestController
public class MyReactiveController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    KafkaSender<String, String> kadkaSender;

    @Autowired
    ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;


    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Person> addPerson(@RequestBody Person p) {
        return doProcess(p);
    }

    //@Transactional
    private Mono<Person> doProcess(Person person) {
        Mono<Person> p = null;
        return reactiveKafkaProducerTemplate.transactionManager().begin()
                .then(reactiveKafkaProducerTemplate.send("mytopic", person.getName()).doOnError(x -> {
                            log.info("inside doError kafka:::");
                            reactiveKafkaProducerTemplate.transactionManager().abort().subscribe(z -> log.info(
                                    "Aborted transaction"));
                        })
                .then( personRepository.save(person)).doOnError(x -> {
                    log.info("inside doError mongo:::");
                    reactiveKafkaProducerTemplate.transactionManager().abort().subscribe(z -> log.info(
                            "Aborted transaction"));
                })
                )
                .then(reactiveKafkaProducerTemplate.transactionManager().commit());
    }

    @KafkaListener(topics = "mytopic", groupId = "foo")
    public void ConsumeMessage(String name) {
        log.info("received message for :::" + name);
    }

}
