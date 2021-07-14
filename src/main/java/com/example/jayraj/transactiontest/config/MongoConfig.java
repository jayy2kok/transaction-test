package com.example.jayraj.transactiontest.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.example.jayraj.transactiontest.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration{

    // rest of the config goes here

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Bean
    ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory cf) {
        return new ReactiveMongoTransactionManager(cf);
    }
}