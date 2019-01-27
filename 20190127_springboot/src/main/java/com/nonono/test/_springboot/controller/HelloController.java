package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.AuthorSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@Slf4j
public class HelloController {

    @Autowired
    private AuthorSettings authorSettings;

    @RequestMapping("/")
    public String index() {
        log.info("hello");
        return "hello world." + "My name is " + authorSettings.getName() + ",I am " + authorSettings.getAge() + " years old.";
    }
}
