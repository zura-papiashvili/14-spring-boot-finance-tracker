package com.example.finance_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.finance_tracker.modules.user")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
