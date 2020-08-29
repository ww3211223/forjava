package com.nonono.test.simple.netty.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AmeNettyConfig {

    /**
     * socket服务端端口
     */
    @Value("${ame.socket.server.port:9006}")
    private int socketServerPort;

    /**
     * socket标识
     */
    @Value("${ame.socket.current.no:99001}")
    private int socketCurrentNo;

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
     * 服务端自动清理周期
     */
    @Value("${ame.socket.server.auto_clean.cron:0 0/5 * * * ?}")
    private String serverAutoCleanCron;

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

    /**
     * 客户端是否自动注册
     */
    @Value("${ame.socket.client.auto_register:true}")
    private boolean socketClientAutoRegister;
}
