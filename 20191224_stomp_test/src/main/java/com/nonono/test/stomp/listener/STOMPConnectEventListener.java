package com.nonono.test.stomp.listener;

import com.nonono.test.stomp.service.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * STOMP监听类
 */
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {
    @Autowired
    private SocketSessionRegistry sessionRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String login = sha.getFirstNativeHeader("login");
        String sessionId = sha.getSessionId();

        sessionRegistry.registerSession(login, sessionId);
    }
}
