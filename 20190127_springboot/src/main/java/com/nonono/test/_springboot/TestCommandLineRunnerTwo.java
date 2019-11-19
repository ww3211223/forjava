package com.nonono.test._springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class TestCommandLineRunnerTwo implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("TestCommandLineRunnerTwo is running.");
    }
}
