package com.nonono.test.cloud.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class ServerTwoStart {

	public static void main(String[] args) {
		SpringApplication.run(ServerTwoStart.class, args);
	}

}
