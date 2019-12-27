package com.nonono.test.stomp.service;

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
    private final Object lock = new Object();

    public Set<String> getSessionId(String user) {
        if (!userSessionMap.containsKey(user)) {
            return null;
        }

        return this.userSessionMap.get(user);
    }

    public Map<String, Set<String>> getAllSessionMap() {
        return userSessionMap;
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
            synchronized (lock) {
                if (!userSessionMap.containsKey(user)) {
                    userSessionMap.put(user, new CopyOnWriteArraySet<>());
                }
            }
        }

        userSessionMap.get(user).add(sessionId);
    }

    /**
     * 注销session
     *
     * @param user
     * @param sessionId
     */
    public void unregisterSession(String user, String sessionId) {
        if (Objects.isNull(user) || Objects.isNull(sessionId)) {
            return;
        }
        if (userSessionMap.containsKey(user)) {
            userSessionMap.get(user).remove(sessionId);
        }
    }
}
