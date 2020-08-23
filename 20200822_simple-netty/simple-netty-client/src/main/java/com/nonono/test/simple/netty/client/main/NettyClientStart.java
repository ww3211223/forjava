package com.nonono.test.simple.netty.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class NettyClientStart {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientStart.class, args);
    }

}
