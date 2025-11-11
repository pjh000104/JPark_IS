package com.example.localcuisine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.localcuisine.entity")
public class LocalcuisineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalcuisineApplication.class, args);
	}

}
