package com.nonono.test.simple.netty.client.controller;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.send.ReStartSocketClient;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ReStartSocketClient nettySocketClient;

    @Value("${simple.netty.current.client_no}")
    private Integer clientNo;

    @GetMapping(value = "/device/register")
    public String testDeviceRegister(String deviceNo) {
        return "ok";
    }

}
