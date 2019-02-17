package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.WiselyMessage;
import com.nonono.test._springboot.model.WiselyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WiselyController {

    //通过SimpMessagingTemplate向浏览器发送消息
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse") //当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息。
    public WiselyResponse say(WiselyMessage message) throws Exception {
        Thread.sleep(3000);
        return new WiselyResponse("Welcome," + message.getName() + "!");
    }

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        if (principal.getName().equals("wyf")) {
            messagingTemplate.convertAndSendToUser("wisely", "/queue/notifications", principal.getName() + "-send:" + msg);
        } else {
            messagingTemplate.convertAndSendToUser("wyf", "/queue/notifications", principal.getName() + "-send:" + msg);
        }
    }
}
