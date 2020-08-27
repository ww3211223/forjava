package com.nonono.test.simple.netty.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * Pong命令处理器接口
 */
public interface IPongCommand {

    /**
     * 处理
     *
     * @param ctx
     */
    void execute(ChannelHandlerContext ctx);
}
