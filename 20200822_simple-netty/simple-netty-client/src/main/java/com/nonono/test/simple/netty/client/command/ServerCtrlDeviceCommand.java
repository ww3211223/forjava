package com.nonono.test.simple.netty.client.command;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.client.model.ServerCtrlDeviceRequest;
import com.nonono.test.simple.netty.core.command.BaseCommand;
import com.nonono.test.simple.netty.core.command.BaseCommandDirective;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServerCtrlDeviceCommand extends BaseCommand<ServerCtrlDeviceRequest> {

    private static final Logger logger = LoggerFactory.getLogger(ServerCtrlDeviceCommand.class);

    @Override
    protected JsonCommand doExecute(ChannelHandlerContext ctx, int clientNo, ServerCtrlDeviceRequest deviceRegisterRequest) {
        logger.info("收到云端指令下发 {}", Jack.toJson(deviceRegisterRequest));
        return null;
    }

    @Override
    public CmdDirective getDirective() {
        return BaseCommandDirective.SERVER_ISSUANCE;
    }

}
