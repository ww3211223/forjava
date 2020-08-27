package com.nonono.test.simple.netty.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
@EnableScheduling
public class NettyClientStart {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientStart.class, args);
    }

}
