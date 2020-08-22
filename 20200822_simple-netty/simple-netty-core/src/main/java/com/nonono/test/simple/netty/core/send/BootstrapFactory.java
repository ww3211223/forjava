package com.nonono.test.simple.netty.core.send;

import com.google.common.collect.Maps;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.processor.RawMessageFactory;
import com.nonono.test.simple.netty.core.processor.impl.PongCommandMessageProcessor;
import com.nonono.test.simple.netty.core.socket.RawMessageInboundHandler;
import com.nonono.test.simple.netty.core.socket.RawMessageOutboundHandler;
import com.nonono.test.simple.netty.core.socket.RobinChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BootstrapFactory {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapFactory.class);

    /**
     *
     */
    public static final BootstrapFactory FACT = new BootstrapFactory();

    /**
     *
     */
    private static final Map<String, Bootstrap> cache = Maps.newConcurrentMap();

    private RobinChannelInitializer robinChannelInitializer;

    private BootstrapFactory() {
        RawMessageEncoder messageEncoder = new RawMessageEncoder();
        RawMessageOutboundHandler rawMessageOutboundHandler = new RawMessageOutboundHandler(messageEncoder);
        RawMessageInboundHandler rawMessageInboundHandler = new RawMessageInboundHandler(messageEncoder);

        robinChannelInitializer = new RobinChannelInitializer(rawMessageOutboundHandler, rawMessageInboundHandler);
        logger.info("BootstrapFactory robinChannelInitializer#{}", robinChannelInitializer);

        RawMessageFactory.register(new PongCommandMessageProcessor());
    }

    /**
     * get or create
     *
     * @param hostAddress addr
     * @param serverPort  port
     * @return bs
     */
    public Bootstrap getOrCreateBootstrap(String hostAddress, int serverPort) {
        String key = hostAddress + ":" + serverPort;
        Bootstrap bs = cache.get(key);
        if (bs != null) {
            return bs;
        }

        synchronized (logger) {
            if ((bs = cache.get(key)) != null) {
                return bs;
            }

            EventLoopGroup bossGroup = new NioEventLoopGroup();

            bs = new Bootstrap();
            bs = bs.option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
            ;
            bs.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .handler(robinChannelInitializer)
            ;

            cache.put(key, bs);
        }

        return bs;
    }

}
