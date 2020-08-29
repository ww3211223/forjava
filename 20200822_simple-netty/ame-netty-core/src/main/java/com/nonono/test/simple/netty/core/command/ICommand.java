package com.nonono.test.simple.netty.core.command;

import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * 通用指令接口
 */
public interface ICommand {
    /**
     * @param ctx
     * @param comm
     * @return
     */
    JsonCommand execute(ChannelHandlerContext ctx, JsonCommand comm);

    /**
     * @return
     */
    CmdDirective getDirective();
}
