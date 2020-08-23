package com.nonono.test.simple.netty.core.processor;

import com.google.common.collect.Maps;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RawMessageFactory {
    private static final Logger logger = LoggerFactory.getLogger(RawMessageFactory.class);

    private static final Map<RawMessageType, RawMessageProcessor> processorMap = Maps.newHashMap();

    public static void register(RawMessageProcessor processor) {
        processorMap.put(processor.messageType(), processor);
        logger.info("register message-type#{} with process#{}", processor.messageType(), processor);
    }

    public static RawMessageProcessor getProcessor(RawMessageType messageType) {
        return processorMap.get(messageType);
    }
}
