package com.nonono.test.simple.netty.client.configs;

import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.send.ReStartSocketClient;
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
    public ReStartSocketClient nettySocketClient(AmeNettyConfig nettyConfig) {
        return new ReStartSocketClient(nettyServerHost, nettyServerPort, nettyConfig);
    }

}
