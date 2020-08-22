package com.nonono.test.simple.netty.core;

import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.processor.impl.JsonCommandMessageProcessor;
import com.nonono.test.simple.netty.core.processor.impl.PingCommandMessageProcessor;
import com.nonono.test.simple.netty.core.server.NettySocketServer;
import com.nonono.test.simple.netty.core.socket.RawMessageInboundHandler;
import com.nonono.test.simple.netty.core.socket.RawMessageOutboundHandler;
import com.nonono.test.simple.netty.core.socket.RobinChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmeNettyAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AmeNettyAutoConfiguration.class);

    @Bean
    public RawMessageEncoder rawMessageEncoder() {
        return new RawMessageEncoder();
    }

    @Bean
    public AmeNettyConfig ameNettyConfig() {
        return new AmeNettyConfig();
    }

    @Bean
    public RawMessageInboundHandler rawMessageInboundHandler(RawMessageEncoder messageEncoder) {
        return new RawMessageInboundHandler(messageEncoder);
    }

    @Bean
    public RawMessageOutboundHandler rawMessageOutboundHandler(RawMessageEncoder messageEncoder) {
        return new RawMessageOutboundHandler(messageEncoder);
    }

    @Bean
    public RobinChannelInitializer robinChannelInitializer(RawMessageOutboundHandler rawMessageOutboundHandler,
                                                           RawMessageInboundHandler rawMessageInboundHandler) {
        return new RobinChannelInitializer(rawMessageOutboundHandler, rawMessageInboundHandler);
    }

    @Bean
    public NettySocketServer nettySocketServer(AmeNettyConfig conf, RobinChannelInitializer robinChannelInitializer) {
        return new NettySocketServer(conf, robinChannelInitializer);
    }

    @Bean
    public JsonCommandMessageProcessor jsonCommandMessageProcessor() {
        return new JsonCommandMessageProcessor();
    }

    @Bean
    public PingCommandMessageProcessor pingCommandMessageProcessor() {
        return new PingCommandMessageProcessor();
    }

}
