package com.example.jayraj.transactiontest.repository;

import com.example.jayraj.transactiontest.model.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends MongoRepository<Person, ObjectId> {
    Person findByName(String name);
}
