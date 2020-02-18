package com.nonono.test.mybaits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableIntercept
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
