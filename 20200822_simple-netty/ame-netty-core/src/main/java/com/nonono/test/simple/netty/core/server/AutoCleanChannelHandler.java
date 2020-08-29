package com.nonono.test.simple.netty.core.server;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;

/**
 * 自动清理通道处理器
 */
public class AutoCleanChannelHandler {

    @Scheduled(cron = "#{ameNettyConfig.getServerAutoCleanCron()}")
    public void cleaning() {
        Collection<ChannelHandlerContext> allHanderContexts = ChannelHandlerFactory.getAllHanderContexts();
        if (CollectionUtils.isNotEmpty(allHanderContexts)) {
            allHanderContexts.forEach(item -> {
                if (item.isRemoved()) {
                    ChannelHandlerFactory.remove(item);
                }
            });
        }
    }
}
