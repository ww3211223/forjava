package com.nonono.test.simple.netty.server.services;


import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ChannelHandlerFactory {
    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerFactory.class);

    private static final Map<Integer, ChannelHandlerContext> handerMap = Maps.newHashMap();

    public static void register(Integer clientNo, ChannelHandlerContext channelHandlerContext) {
        if (handerMap.containsKey(clientNo)) {
            return;
        }
        handerMap.put(clientNo, channelHandlerContext);
        logger.info("register clientNo#{} with channelHandlerContext#{}", clientNo, channelHandlerContext);
    }

    public static ChannelHandlerContext getChannelHandlerContext(Integer clientNo) {
        return handerMap.get(clientNo);
    }
}
