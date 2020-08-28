package com.nonono.test.simple.netty.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
public class AmeNettyConfig {

    /**
     * socket服务端端口
     */
    @Value("${ame.socket.server.port:9006}")
    private int socketServerPort;

    /**
     * 心跳发送周期
     */
    @Value("${ame.socket.client.ping.cron:0/10 * * * * ?}")
    private String clientPingCron;

    /**
     * 存活检测周期
     */
    @Value("${ame.socket.client.self_check.cron: 0/15 * * * * ?}")
    private String clientSelfCheckCron;

    /**
     * 重启检测间隔（秒）
     */
    @Value("${ame.socket.client.check_prestart.second:180}")
    private int clientCheckPrestartSecond;

    /**
     * 心跳检测间隔（秒）
     */
    @Value("${ame.socket.client.check_ping.second:60}")
    private int clientCheckPingSecond;

    /**
     * 客户端断线重连开关
     */
    @Value("${ame.socket.client.restart.enabled:true}")
    private boolean clientRestartEnabled;

    /**
     * 客户端断线重连最大次数
     * max <= 0时，无最大重试次数
     */
    @Value("${ame.socket.client.restart.max:0}")
    private int clientRestartMax;
}
