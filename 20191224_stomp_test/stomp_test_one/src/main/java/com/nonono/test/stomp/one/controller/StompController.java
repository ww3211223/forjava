package com.nonono.test.stomp.one.controller;

import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StompController {

    @MessageMapping("/send")
    public String subscription(String str) throws MessagingException {
        System.out.println("send：" + str);
        return "响应消息：" + str;
    }

    @MessageMapping("/sendUser")
    @SendToUser("/queue/sendUser")
    public String sendUser(String str) {
        System.out.println("sendUser：" + str);
        return "sendUser:" + str;
    }
}
