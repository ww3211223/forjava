package com.nonono.test.simple.netty.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * Pong指令处理器接口
 */
public interface IPongCommand {

    /**
     * 处理pong命令
     *
     * @param ctx
     */
    void executePong(ChannelHandlerContext ctx);
}
