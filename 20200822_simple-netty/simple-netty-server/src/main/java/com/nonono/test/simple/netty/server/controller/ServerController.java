package com.nonono.test.simple.netty.server.controller;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.server.ChannelHandlerFactory;
import com.nonono.test.simple.netty.core.server.NettySocketServer;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import com.nonono.test.simple.netty.server.model.CommandDirective;
import com.nonono.test.simple.netty.server.model.ServerCtrlDeviceRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ServerController {

    @Autowired
    private NettySocketServer nettySocketServer;

    private RawMessageEncoder messageEncoder = new RawMessageEncoder();

    @GetMapping(value = "/server/ctrl_device")
    public String testDeviceRegister(Integer clientNo, String instruct) {

        ServerCtrlDeviceRequest registerRequest = new ServerCtrlDeviceRequest();
        registerRequest.setInstruct(instruct);
        JsonCommand command = new JsonCommand();
        command.setDirective(CommandDirective.SERVER_CTRL_DEVICE);
        command.setRequestId(0L);
        command.setClientNo(clientNo);
        command.setDirectiveVal(CommandDirective.SERVER_CTRL_DEVICE.getCode());
        command.setData(Jack.toJson(registerRequest));
        command.setStatus(0);
        command.setMessage("test request.");

        ChannelHandlerContext channelHandlerContext = ChannelHandlerFactory.getChannelHandlerContext(clientNo);
        if (Objects.isNull(channelHandlerContext)) {
            return "channelHandlerContext is null.";
        }

        RawMessage message = RawMessages.buildJsonMessage(clientNo, Jack.toJson(command));
        byte[] bytesData = messageEncoder.encode(message);
        ByteBuf outBuf = Unpooled.copiedBuffer(bytesData);
        channelHandlerContext.channel().writeAndFlush(outBuf);
        return "ok";
    }

}
