package com.nonono.test.simple.netty.core.send;

import com.nonono.test.simple.netty.core.command.BasePongCommand;
import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * socket客户端监视者
 */
public class SocketClientSupervisor extends BasePongCommand {

    private static final Logger log = LoggerFactory.getLogger(SocketClientSupervisor.class);

    private NettySocketClient nettySocketClient;

    private AmeNettyConfig nettyConfig;

    /**
     * 是否连接
     */
    private boolean isConnected;

    /**
     * 预连接
     */
    private boolean preConnected;

    /**
     * 服务端最后响应时间
     */
    private LocalDateTime lastPongTime;

    /**
     * 最后预启动时间
     */
    private LocalDateTime lastPreStartTime;

    /**
     * 重启次数
     */
    private int restartQuantity;

    public SocketClientSupervisor(AmeNettyConfig nettyConfig, NettySocketClient nettySocketClient) {
        this.nettySocketClient = nettySocketClient;
        this.nettyConfig = nettyConfig;
    }

    /**
     * 启动socket连接
     */
    public void startSocket() {
        preConnected = true;
        restartQuantity++;
        lastPreStartTime = LocalDateTime.now();
        lastPongTime = null;
    }

    /**
     * 重启socket连接
     */
    private void restartSocket() {
        nettySocketClient.start();
    }

    @Scheduled(cron = "#{nettyConfig.getClientPingCron()}")
    public void ping() {
        if (!nettyConfig.isClientRestartEnabled()) {
            return;
        }
        if (preConnected || isConnected) {
            RawMessage message = new RawMessage();
            RawMessages.from(RawMessageType.PING_COMMAND, 0, "ping");
            nettySocketClient.send(message);
        }
    }

    @Scheduled(cron = "#{nettyConfig.getClientSelfCheckCron()}")
    public void socketCheck() {
        if (!nettyConfig.isClientRestartEnabled()
                || (nettyConfig.getClientRestartMax() > 0 && restartQuantity > nettyConfig.getClientRestartMax())) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();

        //连接检测重启
        if (preConnected && !isConnected) {
            Duration duration = Duration.between(lastPreStartTime, now);
            if (duration.getSeconds() > restartQuantity * nettyConfig.getClientCheckPrestartSecond()) {
                restartSocket();
            }
        }
        //心跳检测
        if (isConnected) {
            Duration duration = Duration.between(Objects.isNull(lastPongTime) ? lastPreStartTime : lastPongTime, now);
            if (duration.getSeconds() > nettyConfig.getClientCheckPingSecond()) {
                isConnected = false;
                log.warn("socket is closed.");
            }
        }
    }

    @Override
    public void execute(ChannelHandlerContext ctx) {
        if (!isConnected) {
            isConnected = true;
            restartQuantity = 1;
            log.info("socket started.");
        }
        this.lastPongTime = LocalDateTime.now();
    }
}
