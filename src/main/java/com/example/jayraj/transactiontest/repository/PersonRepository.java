package com.example.jayraj.transactiontest.repository;

import com.example.jayraj.transactiontest.model.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, ObjectId> {
    Mono<Person> findByName(String name);
}
