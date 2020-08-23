package com.nonono.test.simple.netty.server.command;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.core.command.BaseCommand;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.server.model.CommandDirective;
import com.nonono.test.simple.netty.server.model.DeviceRegisterCallbackRequest;
import com.nonono.test.simple.netty.server.model.DeviceRegisterRequest;
import com.nonono.test.simple.netty.server.services.ChannelHandlerFactory;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeviceRegisterCommand extends BaseCommand<DeviceRegisterRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DeviceRegisterCommand.class);

    @Override
    protected JsonCommand doExecute(ChannelHandlerContext ctx, int clientNo, DeviceRegisterRequest deviceRegisterRequest) {
        logger.info("收到注册请求 {}", Jack.toJson(deviceRegisterRequest));
        DeviceRegisterCallbackRequest registerCallback = new DeviceRegisterCallbackRequest();
        registerCallback.setDeviceId(deviceRegisterRequest.getDeviceId());
        registerCallback.setMessage("注册完成");

        ChannelHandlerFactory.register(clientNo, ctx);
        JsonCommand command = new JsonCommand();
        command.setDirective(CommandDirective.RESP_DEVICE_REGISTER);
        command.setRequestId(0L);
        command.setClientNo(clientNo);
        command.setDirectiveVal(CommandDirective.RESP_DEVICE_REGISTER.getCode());
        command.setData(Jack.toJson(registerCallback));
        command.setStatus(0);
        command.setMessage("test callback.");
        return command;
    }

    @Override
    public CmdDirective getDirective() {
        return CommandDirective.DEVICE_REGISTER;
    }

}
