package com.nonono.test.simple.netty.client.job;

import com.nonono.test.simple.netty.core.send.NettySocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyRunner implements ApplicationRunner {

    @Autowired
    private NettySocketClient nettySocketClient;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettySocketClient.start();
    }
}
