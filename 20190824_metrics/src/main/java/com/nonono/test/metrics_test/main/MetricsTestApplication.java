package com.nonono.test.metrics_test.main;

import com.codahale.metrics.Slf4jReporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
@PropertySource({"classpath:application.properties"})
public class MetricsTestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MetricsTestApplication.class, args);
//        ConsoleReporter reporter = context.getBean(ConsoleReporter.class);
        Slf4jReporter reporter = context.getBean(Slf4jReporter.class);
        reporter.start(5, TimeUnit.SECONDS);
    }

}
