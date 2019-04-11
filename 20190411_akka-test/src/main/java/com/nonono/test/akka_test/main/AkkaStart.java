package com.nonono.test.akka_test.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world!
 */

@SpringBootApplication(scanBasePackages = "com.nonono.test", exclude = {DataSourceAutoConfiguration.class})
@PropertySource({"classpath:application.properties"})
public class AkkaStart {
    public static void main(String[] args) {
        SpringApplication.run(AkkaStart.class, args);
    }
}
