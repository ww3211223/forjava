package com.nonono.test.simple.netty.core.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class RobinChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RawMessageOutboundHandler rawMessageOutboundHandler;

    private RawMessageInboundHandler rawMessageInboundHandler;

    public RobinChannelInitializer(RawMessageOutboundHandler rawMessageOutboundHandler,
                                   RawMessageInboundHandler rawMessageInboundHandler) {
        this.rawMessageOutboundHandler = rawMessageOutboundHandler;
        this.rawMessageInboundHandler = rawMessageInboundHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(rawMessageOutboundHandler);
        pipeline.addLast(rawMessageInboundHandler);
    }
}
