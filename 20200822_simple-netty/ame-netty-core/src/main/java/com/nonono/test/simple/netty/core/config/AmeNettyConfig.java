package com.nonono.test.simple.netty.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AmeNettyConfig {

    @Value("${ame.socket.server.port:9006}")
    private int socketServerPort;
}