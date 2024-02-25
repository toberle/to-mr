package com.github.toberle.tomr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionalOutboxMessageRelayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionalOutboxMessageRelayApplication.class, args);
	}
}
