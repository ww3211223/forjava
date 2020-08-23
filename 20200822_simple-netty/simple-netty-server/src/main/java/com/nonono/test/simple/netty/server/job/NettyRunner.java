package com.nonono.test.simple.netty.server.job;

import com.nonono.test.simple.netty.core.server.NettySocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

@Component
public class NettyRunner implements ApplicationRunner {

    @Autowired
    private NettySocketServer nettySocketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettySocketServer.start();
    }
}
