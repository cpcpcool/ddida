package com.runner.ddida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DdidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdidaApplication.class, args);
	}

}
