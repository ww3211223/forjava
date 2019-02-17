package com.nonono.test._springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ChatController {

    @RequestMapping("/chat")
    public ModelAndView chat(Principal principal) {
        ModelAndView mv = new ModelAndView("chat");
        mv.addObject("principal", principal);
        return mv;
    }
}
