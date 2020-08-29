package com.nonono.test.simple.netty.server.controller;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.core.command.BaseCommandDirective;
import com.nonono.test.simple.netty.core.config.AmeNettyConfig;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.server.ChannelHandlerFactory;
import com.nonono.test.simple.netty.core.server.NettySocketServer;
import com.nonono.test.simple.netty.core.utils.RawMessages;
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

    @Autowired
    private AmeNettyConfig config;

    private RawMessageEncoder messageEncoder = new RawMessageEncoder();

    @GetMapping(value = "/server/ctrl_device")
    public String testDeviceRegister(String clientIdentity, String instruct) {
        ChannelHandlerContext channelHandlerContext = ChannelHandlerFactory.getChannelHandlerContext(clientIdentity);
        if (Objects.isNull(channelHandlerContext)) {
            return "channelHandlerContext is null.";
        }

        ServerCtrlDeviceRequest registerRequest = new ServerCtrlDeviceRequest();
        registerRequest.setInstruct(instruct);
        JsonCommand command = new JsonCommand();
        command.setDirective(BaseCommandDirective.SERVER_ISSUANCE);
        command.setRequestId(0L);
        command.setClientNo(config.getSocketCurrentNo());
        command.setDirectiveVal(BaseCommandDirective.SERVER_ISSUANCE.getCode());
        command.setData(Jack.toJson(registerRequest));
        command.setStatus(0);
        command.setMessage("test request.");

        RawMessage message = RawMessages.buildJsonMessage(config.getSocketCurrentNo(), Jack.toJson(command));
        byte[] bytesData = messageEncoder.encode(message);
        ByteBuf outBuf = Unpooled.copiedBuffer(bytesData);
        channelHandlerContext.channel().writeAndFlush(outBuf);
        return "ok";
    }
}
