package com.example.jayraj.transactiontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableTransactionManagement
public class TransactionTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionTestApplication.class, args);
	}

}
