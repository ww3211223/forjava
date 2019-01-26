package com.nonono.test.controller;

import com.nonono.test.model.DemoObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class HelloController {

    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/class")
    @ResponseBody
    public DemoObj testClass() {
        return DemoObj.builder().id(10L).name("艾玛").build();
    }
}
