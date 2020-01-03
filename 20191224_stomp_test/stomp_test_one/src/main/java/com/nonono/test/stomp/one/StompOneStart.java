package com.nonono.test.stomp.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class StompOneStart {

	public static void main(String[] args) {
		SpringApplication.run(StompOneStart.class, args);
	}

}
