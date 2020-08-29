package com.nonono.test.simple.netty.core.server;


import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 通道处理器工厂
 */
public class ChannelHandlerFactory {
    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerFactory.class);

    private static final Map<String, ChannelHandlerContext> handerMap = Maps.newHashMap();

    public static void register(String clientIdentity, ChannelHandlerContext channelHandlerContext) {
        if (handerMap.containsKey(clientIdentity)) {
            return;
        }
        handerMap.put(clientIdentity, channelHandlerContext);
        logger.info("register clientIdentity#{} with channelHandlerContext#{}", clientIdentity, channelHandlerContext);
    }

    public static boolean contains(String clientIdentity) {
        return handerMap.containsKey(clientIdentity);
    }

    public static ChannelHandlerContext getChannelHandlerContext(String clientIdentity) {
        return handerMap.get(clientIdentity);
    }

    public static void remove(ChannelHandlerContext ctx) {
        handerMap.remove(ctx);
    }

    /**
     * 获取所有通道处理器
     *
     * @return
     */
    public static Collection<ChannelHandlerContext> getAllHanderContexts() {
        return handerMap.values();
    }
}
