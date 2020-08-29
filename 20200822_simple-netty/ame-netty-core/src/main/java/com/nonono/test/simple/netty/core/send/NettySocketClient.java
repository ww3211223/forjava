package com.nonono.test.simple.netty.core.send;

import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nettySocket客户端
 */
public class NettySocketClient {

    private static final Logger logger = LoggerFactory.getLogger(NettySocketClient.class);

    private String hostAddress;

    private int serverPort;

    private ChannelFuture clientChannel;

    private RawMessageEncoder messageEncoder = new RawMessageEncoder();

    public NettySocketClient(String hostAddress, int serverPort) {
        this.hostAddress = hostAddress;
        this.serverPort = serverPort;
    }

    /**
     * 创建并启动socket
     */
    public void start() {
        Bootstrap bootstrap = BootstrapFactory.FACT.getOrCreateBootstrap(hostAddress, serverPort);
        try {
            clientChannel = bootstrap.connect(hostAddress, serverPort).sync();
        } catch (InterruptedException e) {
            logger.error("connect socket error.", e);
        }

        logger.info("clientChannel is {}", clientChannel);
        clientChannel.channel().closeFuture().addListener((f) -> {
            logger.info("client channel closed, f is {}", f.getNow());
        });
        logger.info("netty client start done");
        this.postStart();
    }

    /**
     * 启动socket后续处理
     */
    protected void postStart() {

    }

    /**
     * send raw message
     *
     * @param message msg
     */
    public void send(RawMessage message) {
        byte[] bytesData = messageEncoder.encode(message);
        ByteBuf outBuf = Unpooled.copiedBuffer(bytesData);
        clientChannel.channel().writeAndFlush(outBuf);
    }

}
