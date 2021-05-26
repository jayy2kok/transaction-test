package com.example.jayraj.transactiontest.controller;

import com.example.jayraj.transactiontest.model.Person;
import com.example.jayraj.transactiontest.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
@RestController
public class MyReactiveController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Person> addPerson(@RequestBody Person p) {
        return doProcess(p);
    }

   @Transactional(propagation = Propagation.MANDATORY)
    Mono<Person> doProcess(Person p) {

        return kafkaTemplate.executeInTransaction(kafkaTemplate -> {
            try {
                kafkaTemplate.send("mytopic", "Inserting ::" + p.getName()).get(5, TimeUnit.SECONDS);
                return Mono.just(personRepository.save(p));
            } catch (InterruptedException | ExecutionException | TimeoutException e){
                log.error(e);
            }
            throw new RuntimeException("Unable to process request");
        });
    }

    @KafkaListener(topics = "mytopic", groupId = "foo")
    public void listenGroupFoo(String message) {
        log.info("Received Message in group foo::: " + message);
    }
}
