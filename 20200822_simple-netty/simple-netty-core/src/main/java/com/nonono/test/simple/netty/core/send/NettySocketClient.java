package com.nonono.test.simple.netty.core.send;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NettySocketClient {

    private static final Logger logger = LoggerFactory.getLogger(NettySocketClient.class);


    /**
     *
     */
    private String hostAddress;

    /**
     *
     */
    private int serverPort;

    /**
     *
     */
    private ChannelFuture clientChannel;

    /**
     *
     */
    private RawMessageEncoder messageEncoder = new RawMessageEncoder();

    public NettySocketClient(String hostAddress, int serverPort) {
        this.hostAddress = hostAddress;
        this.serverPort = serverPort;
    }

    /**
     *
     */
    public void start() {
        CountDownLatch latch = new CountDownLatch(1);
//        new Thread(() -> {
//
//        })
//                .start();

        try {
            Bootstrap bootstrap = BootstrapFactory.FACT.getOrCreateBootstrap(hostAddress, serverPort);

            clientChannel = bootstrap.connect(hostAddress, serverPort).sync();

            logger.info("clientChannel is {}", clientChannel);

            clientChannel.channel().closeFuture().addListener((f) -> {
                logger.info("client channel closed, f is {}", f.getNow());
            });
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            latch.countDown();
        }

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("", e);
        }

        logger.info("netty client start done");
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
