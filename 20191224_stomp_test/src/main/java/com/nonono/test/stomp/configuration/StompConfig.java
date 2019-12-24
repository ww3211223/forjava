package com.nonono.test.stomp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * 注册STOMP协议终结点
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        //将客户端标识封装为Principal对象，从而让服务端能够通过getName()方法找到指定客户端
                        Object o = attributes.get("name");
                        if (o == null) {
                            return new FastPrincipal(null);
                        }

                        return new FastPrincipal(o.toString());
                    }
                })
                //添加socket拦截器，用于从请求中获取客户端标识参数
                .addInterceptors()
                .withSockJS();
    }

    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端发送消息的请求前缀
        registry.setApplicationDestinationPrefixes("/app");
        //客户端订阅消息的请求前缀，topic用于广播推送，queue用于点对点推送
        registry.enableSimpleBroker("/topic", "/queue");
        //服务端通知客户端的前缀，默认/user
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 定义权限验证类
     */
    class FastPrincipal implements Principal {
        private final String name;

        public FastPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
