package com.nonono.test.simple.netty.client.configs;

import com.nonono.test.simple.netty.core.send.NettySocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyClientConfiguration {

    @Value("${simple.netty.server.host}")
    private String nettyServerHost;

    @Value("${simple.netty.server.port}")
    private Integer nettyServerPort;

    @Bean
    public NettySocketClient nettySocketClient() {
        return new NettySocketClient(nettyServerHost, nettyServerPort);
    }

}
