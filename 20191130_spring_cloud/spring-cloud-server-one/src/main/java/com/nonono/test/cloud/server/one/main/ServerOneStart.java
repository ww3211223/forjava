package com.nonono.test.cloud.server.one.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableCircuitBreaker
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class ServerOneStart {

    public static void main(String[] args) {
        SpringApplication.run(ServerOneStart.class, args);
    }

}
