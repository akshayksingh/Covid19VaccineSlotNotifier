package com.test.notify.covid19vaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Covid19vaccineApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19vaccineApplication.class, args);
	}

}
