package com.nonono.test.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StompController {

    @Autowired
    SimpMessagingTemplate SMT;

    @MessageMapping("/send")
    public void subscription(String str) throws MessagingException {
        System.out.println("发送消息：" + str);
        SMT.convertAndSend("/topic/sub", str);
    }

}
