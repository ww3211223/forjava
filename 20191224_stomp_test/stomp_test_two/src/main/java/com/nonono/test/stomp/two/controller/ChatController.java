package com.nonono.test.stomp.two.controller;

import com.nonono.test.stomp.two.service.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    SimpMessagingTemplate SMT;

    @Autowired
    private SocketSessionRegistry sessionRegistry;

    @RequestMapping("/send")
    @ResponseBody
    public String send(@RequestParam("message") String message) {
        SMT.convertAndSend("/topic/answer", "推送消息：" + message);
        return "ok";
    }

    @RequestMapping("/sendUser/{user}")
    @ResponseBody
    public String sendUser(@PathVariable("user") String user, @RequestParam("message") String message) {
        Set<String> sessionIdSet = sessionRegistry.getSessionId(user);
        if (CollectionUtils.isEmpty(sessionIdSet)) {
            return "fail";
        }
        sessionIdSet.forEach(item -> {
            SMT.convertAndSendToUser(item, "/queue/answer", "推送消息：" + message, createHeaders(item));
        });

        return "ok";
    }

    /**
     * 创建Headers
     *
     * @param sessionId
     * @return
     */
    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
