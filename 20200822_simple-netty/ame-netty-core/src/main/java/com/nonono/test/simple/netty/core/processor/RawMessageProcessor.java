package com.nonono.test.simple.netty.core.processor;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;

public interface RawMessageProcessor {
    /**
     * 根据 <code>request</code>, 处理
     *
     * @param request
     * @return response
     */
    RawMessage process(RawMessage request);

    /**
     * @return
     */
    RawMessageType messageType();
}
