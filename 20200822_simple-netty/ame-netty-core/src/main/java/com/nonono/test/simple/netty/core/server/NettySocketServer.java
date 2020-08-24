package com.nonono.test.simple.netty.core.server;

import com.google.common.collect.Maps;
import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.socket.RobinChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class NettySocketServer {

    private static final Logger logger = LoggerFactory.getLogger(NettySocketServer.class);

    private boolean isRunning;

    private AmeNettyConfig conf;

    private RobinChannelInitializer robinChannelInitializer;

    public NettySocketServer(AmeNettyConfig conf, RobinChannelInitializer robinChannelInitializer) {
        this.conf = conf;
        this.robinChannelInitializer = robinChannelInitializer;
    }

    public void start() {
        start(conf.getSocketServerPort());
    }

    public void start(int port) {
        logger.info("begin run netty-server on port#{}", port);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b = b.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            // bootstrap.setOption("reuseAddress", true);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(robinChannelInitializer)
            ;

            ChannelFuture bindFuture = b.bind(port);

            bindFuture.addListener(future -> logger.info("bind done"));

            ChannelFuture syncFuture = bindFuture.sync();

            syncFuture.addListener(future -> {
                logger.info("syn done");
                isRunning = true;
            });

            ChannelFuture closeFuture = syncFuture.channel().closeFuture();

            closeFuture.addListener(future -> logger.info("close done"));

            Runtime.getRuntime().removeShutdownHook(new Thread(() -> logger.info("shutdown...")));

            logger.info("before close future sync");

            closeFuture.sync();

        } catch (InterruptedException e) {
            throw new RuntimeException("", e);
        } finally {
            logger.info("shutdown netty-server");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
