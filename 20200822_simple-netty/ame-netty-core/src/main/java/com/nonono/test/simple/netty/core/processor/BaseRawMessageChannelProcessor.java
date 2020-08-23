package com.nonono.test.simple.netty.core.processor;

import com.nonono.test.simple.netty.core.message.RawMessage;

public abstract class BaseRawMessageChannelProcessor extends BaseRawMessageProcessor implements RawMessageChannelProcessor {

    @Override
    public RawMessage process(RawMessage message) {
        throw new RuntimeException("unsupported method");
    }
}
