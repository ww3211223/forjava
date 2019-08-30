package com.nonono.test.configurationFirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
@PropertySource(value = {"classpath:/test/test2.properties"}, encoding = "utf-8")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
