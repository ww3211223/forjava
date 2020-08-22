package com.nonono.test.simple.netty.core.processor.impl;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.processor.BaseRawMessageChannelProcessor;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingCommandMessageProcessor extends BaseRawMessageChannelProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PingCommandMessageProcessor.class);

    @Override
    public RawMessage process(ChannelHandlerContext ctx, RawMessage msg) {
        return RawMessages.from(RawMessageType.PONG_COMMAND, msg.getClientNo(), "pong");
    }

    @Override
    public RawMessageType messageType() {
        return RawMessageType.PING_COMMAND;
    }
}
