package com.nonono.test.findBean;

import org.springframework.stereotype.Service;

@Service
public class FunctionService {

    public String sayHello(String msg) {
        return "Hello" + msg;
    }

}
