package com.nonono.test.aop;

public class HelloServiceImpl implements HelloService {
    @Override
    public void work() {
        System.out.println("hello world.");
    }
}
