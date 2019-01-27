package com.nonono.test.starter_hello;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {

    private static final String DAFAULT_MSG = "world";
    private String msg = DAFAULT_MSG;
}
