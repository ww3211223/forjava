package com.nonono.test.stomp.two.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * socket 会话注册类
 */
@Component
public class SocketSessionRegistry {

    private final Map<String, Set<String>> userSessionMap = new ConcurrentHashMap<>();
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public Set<String> getSessionId(String user) {
        if (!userSessionMap.containsKey(user)) {
            return null;
        }

        return this.userSessionMap.get(user);
    }

    /**
     * 注册session
     *
     * @param user
     * @param sessionId
     */
    public void registerSession(String user, String sessionId) {
        if (Objects.isNull(user) || Objects.isNull(sessionId)) {
            return;
        }
        if (!userSessionMap.containsKey(user)) {
            userSessionMap.put(user, new CopyOnWriteArraySet<>());
        }

        userSessionMap.get(user).add(sessionId);
        sessionUserMap.putIfAbsent(sessionId, user);
    }

    /**
     * 注销session
     *
     * @param sessionId
     */
    public void unregisterSession(String sessionId) {
        if (Objects.isNull(sessionId)) {
            return;
        }
        String user = sessionUserMap.getOrDefault(sessionId, null);
        if (Objects.isNull(user)) {
            return;
        }

        if (userSessionMap.containsKey(user)) {
            userSessionMap.get(user).remove(sessionId);
        }
    }
}
