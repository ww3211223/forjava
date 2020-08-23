package com.nonono.test.simple.netty.client.job;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.client.model.CommandDirective;
import com.nonono.test.simple.netty.client.model.DeviceRegisterRequest;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.send.NettySocketClient;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
