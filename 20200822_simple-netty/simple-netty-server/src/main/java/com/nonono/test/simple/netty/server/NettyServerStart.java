package com.nonono.test.simple.netty.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nonono.test")
public class NettyServerStart {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerStart.class, args);
    }

}
