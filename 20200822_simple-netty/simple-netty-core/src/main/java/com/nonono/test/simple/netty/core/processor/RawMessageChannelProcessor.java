package com.nonono.test.simple.netty.core.processor;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import io.netty.channel.ChannelHandlerContext;

public interface RawMessageChannelProcessor extends RawMessageProcessor {

    /**
     * 根据 <code>request</code>, 处理
     *
     * @param ctx
     * @param message
     * @return
     */
    RawMessage process(ChannelHandlerContext ctx, RawMessage message);

    /**
     * @return
     */
    RawMessageType messageType();
}
