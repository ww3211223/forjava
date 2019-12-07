package com.nonono.test.cloud.registry.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistryOneStart {

	public static void main(String[] args) {
		SpringApplication.run(RegistryOneStart.class, args);
	}

}
