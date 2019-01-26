package com.nonono.test.findBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseFunctionService {

    @Autowired
    private FunctionService functionService;

    public String sayHello(String msg) {
        return functionService.sayHello(msg);
    }

}
