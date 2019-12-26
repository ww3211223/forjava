package com.nonono.test.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    SimpMessagingTemplate SMT;

    @RequestMapping("")
    public ModelAndView chat() {
        ModelAndView mv = new ModelAndView("chat");
        return mv;
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(@RequestParam("message") String message) {
        SMT.convertAndSend("/topic/answer", "推送消息：" + message);
        return "ok";
    }

    @RequestMapping("/sendUser/{user}")
    @ResponseBody
    public String sendUser(@PathParam("user") String user, @RequestParam("message") String message) {
        SMT.convertAndSendToUser(user, "/queue/answer", "推送消息：" + message);
        return "ok";
    }
}
