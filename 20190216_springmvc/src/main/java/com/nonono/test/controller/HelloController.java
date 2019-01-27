package com.nonono.test.controller;

import com.nonono.test.model.DemoObj;
import com.nonono.test.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class HelloController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/testUnit")
    public ModelAndView testUnit() {
        ModelAndView result = new ModelAndView("index");
        result.addObject("msg", demoService.saySomething());
        return result;
    }

    @RequestMapping(value = "/class")
    @ResponseBody
    public DemoObj testClass() {
        return DemoObj.builder().id(10L).name("艾玛").build();
    }

    @RequestMapping(value = "/rest", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String testRest() {
        return demoService.saySomething();
    }


}
