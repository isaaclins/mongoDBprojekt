package com.backend.AutoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.backend.AutoDB.repository")
@SpringBootApplication
public class AutoDBApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoDBApplication.class, args);
	}
}
