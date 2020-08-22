package com.nonono.test.simple.netty.core.processor;

import org.springframework.beans.factory.InitializingBean;

public abstract class BaseRawMessageProcessor implements RawMessageProcessor, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        RawMessageFactory.register(this);
    }
}
