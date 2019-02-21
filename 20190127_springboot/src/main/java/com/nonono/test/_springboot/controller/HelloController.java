package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.AuthorSettings;
import com.nonono.test._springboot.model.Person;
import com.nonono.test.starter_hello.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@Slf4j
public class HelloController {

    @Autowired
    private AuthorSettings authorSettings;

    @Autowired
    private HelloService helloService;

    @RequestMapping("/")
    public String index() {
        log.info("hello");
        return "hello world." + "My name is " + authorSettings.getName() + ",I am " + authorSettings.getAge() + " years old.";
    }

    @RequestMapping("/sayHello")
    public String sayHello() {
        return helloService.sayHello();
    }

    @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person search(@RequestParam("personName") String personName) {
        return new Person(personName, 32, "shanghai");
    }
}
