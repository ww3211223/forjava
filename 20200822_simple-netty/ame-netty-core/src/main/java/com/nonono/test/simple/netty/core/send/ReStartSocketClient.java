package com.nonono.test.simple.netty.core.send;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.core.command.BaseCommandDirective;
import com.nonono.test.simple.netty.core.command.CommandProcessorFactory;
import com.nonono.test.simple.netty.core.command.IPongCommand;
import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.dto.ClientRegisterRequest;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 重连socket客户端
 */
public class ReStartSocketClient extends NettySocketClient implements IPongCommand, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ReStartSocketClient.class);

    private AmeNettyConfig nettyConfig;

    /**
     * client标识
     */
    private String clientIdentify;

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

    public ReStartSocketClient(String hostAddress, int serverPort, String clientIdentify, AmeNettyConfig nettyConfig) {
        super(hostAddress, serverPort);
        this.nettyConfig = nettyConfig;
        this.clientIdentify = clientIdentify;
    }

    /**
     * 启动socket后续处理
     */
    @Override
    public void postStart() {
        preConnected = true;
        restartQuantity++;
        lastPreStartTime = LocalDateTime.now();
        lastPongTime = null;
        if (nettyConfig.isSocketClientAutoRegister()) {
            ClientRegisterRequest registerRequest = new ClientRegisterRequest();
            registerRequest.setClientIdentify(clientIdentify);
            JsonCommand command = new JsonCommand();
            command.setDirective(BaseCommandDirective.CLIENT_REGISTER);
            command.setDirectiveVal(BaseCommandDirective.CLIENT_REGISTER.getCode());
            command.setData(Jack.toJson(registerRequest));
            command.setStatus(0);
            command.setMessage("client register");

            RawMessage message = RawMessages.buildJsonMessage(nettyConfig.getSocketCurrentNo(), Jack.toJson(command));
            this.send(message);
        }
    }

    /**
     * 重启socket连接
     */
    private void restartSocket() {
        this.start();
    }

    @Scheduled(cron = "#{ameNettyConfig.getClientPingCron()}")
    public void ping() {
        if (!nettyConfig.isClientRestartEnabled()) {
            return;
        }
        if (preConnected || isConnected) {
            RawMessage message = RawMessages.from(RawMessageType.PING_COMMAND, 0, "ping");
            send(message);
        }
    }

    @Scheduled(cron = "#{ameNettyConfig.getClientSelfCheckCron()}")
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
    public void executePong(ChannelHandlerContext ctx) {
        if (!isConnected) {
            isConnected = true;
            restartQuantity = 1;
            log.info("socket started.");
        }
        this.lastPongTime = LocalDateTime.now();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CommandProcessorFactory.register(this);
    }
}
