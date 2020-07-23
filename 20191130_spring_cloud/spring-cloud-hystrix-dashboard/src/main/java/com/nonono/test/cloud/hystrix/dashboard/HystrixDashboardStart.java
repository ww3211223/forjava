package com.nonono.test.cloud.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardStart {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardStart.class, args);
	}

}
