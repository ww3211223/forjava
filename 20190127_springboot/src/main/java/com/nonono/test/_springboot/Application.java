package com.nonono.test._springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.nonono.test", exclude = {DataSourceAutoConfiguration.class})
@PropertySource(value = "classpath:test.properties", ignoreResourceNotFound = true, encoding = "utf-8")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

