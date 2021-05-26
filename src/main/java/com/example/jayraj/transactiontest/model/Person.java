package com.example.jayraj.transactiontest.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Persons")
@Data
public class Person {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String name;
    private int age;
}
