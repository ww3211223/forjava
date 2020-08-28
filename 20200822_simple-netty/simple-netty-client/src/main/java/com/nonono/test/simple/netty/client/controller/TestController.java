package com.nonono.test.simple.netty.client.controller;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.client.model.CommandDirective;
import com.nonono.test.simple.netty.client.model.DeviceRegisterRequest;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.send.NettySocketClient;
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

        DeviceRegisterRequest registerRequest = new DeviceRegisterRequest();
        registerRequest.setDeviceId(deviceNo);
        JsonCommand command = new JsonCommand();
        command.setDirective(CommandDirective.DEVICE_REGISTER);
        command.setRequestId(0L);
        command.setClientNo(clientNo);
        command.setDirectiveVal(CommandDirective.DEVICE_REGISTER.getCode());
        command.setData(Jack.toJson(registerRequest));
        command.setStatus(0);
        command.setMessage("test request.");

        RawMessage message = RawMessages.buildJsonMessage(clientNo, Jack.toJson(command));
        nettySocketClient.send(message);
        return "ok";
    }

}
