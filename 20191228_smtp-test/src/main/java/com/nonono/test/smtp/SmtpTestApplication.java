package com.nonono.test.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class SmtpTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmtpTestApplication.class, args);
	}

}
