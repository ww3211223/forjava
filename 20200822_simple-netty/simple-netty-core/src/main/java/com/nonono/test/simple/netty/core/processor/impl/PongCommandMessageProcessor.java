package com.nonono.test.simple.netty.core.processor.impl;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.processor.BaseRawMessageChannelProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PongCommandMessageProcessor extends BaseRawMessageChannelProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PongCommandMessageProcessor.class);

    @Override
    public RawMessage process(ChannelHandlerContext ctx, RawMessage msg) {
        logger.info("pong");
        System.out.println("pong out");
        return null;
    }

    @Override
    public RawMessageType messageType() {
        return RawMessageType.PONG_COMMAND;
    }
}
