package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.WiselyMessage;
import com.nonono.test._springboot.model.WiselyResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WiselyController {

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse") //当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息。
    public WiselyResponse say(WiselyMessage message) throws Exception {
        Thread.sleep(3000);
        return new WiselyResponse("Welcome," + message.getName() + "!");
    }
}
