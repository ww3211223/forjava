package com.nonono.test.stomp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("")
    public ModelAndView chat() {
        ModelAndView mv = new ModelAndView("chat");
        return mv;
    }
}
