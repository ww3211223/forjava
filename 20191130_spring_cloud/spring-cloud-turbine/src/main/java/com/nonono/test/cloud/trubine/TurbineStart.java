package com.nonono.test.cloud.trubine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
public class TurbineStart {

	public static void main(String[] args) {
		SpringApplication.run(TurbineStart.class, args);
	}

}
