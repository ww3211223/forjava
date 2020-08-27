package com.nonono.test.simple.netty.core.command;

import org.springframework.beans.factory.InitializingBean;

/**
 * Pong命令处理器基类
 */
public abstract class BasePongCommand implements IPongCommand, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        CommandProcessorFactory.registerPongCommand(this);
    }
}
