package com.springsecurtiyimpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringSecrutiyimplApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecrutiyimplApplication.class, args);
	}

}
