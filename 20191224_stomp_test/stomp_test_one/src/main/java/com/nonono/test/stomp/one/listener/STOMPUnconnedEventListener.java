package com.nonono.test.stomp.one.listener;

import com.nonono.test.stomp.one.service.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * STOMP监听类 监听断开连接消息
 */
@Component
public class STOMPUnconnedEventListener implements ApplicationListener<SessionDisconnectEvent> {
    @Autowired
    private SocketSessionRegistry sessionRegistry;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();

        System.out.println(String.format("收到断开消息：%s", sessionId));
        sessionRegistry.unregisterSession(sessionId);
    }
}
