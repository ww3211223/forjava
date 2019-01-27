package com.nonono.test.starter_hello;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloService {

    private String msg;

    public String sayHello() {
        return "Hello: " + msg;
    }
}
