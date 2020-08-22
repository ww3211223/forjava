package com.nonono.test.simple.netty.server;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.core.command.BaseCommand;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceRegisterCommand extends BaseCommand<DeviceRegisterRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DeviceRegisterCommand.class);

    @Override
    protected JsonCommand doExecute(ChannelHandlerContext ctx, int clintNo, DeviceRegisterRequest deviceRegisterRequest) {
        // 执行业务逻辑
        // TODO: 保存近端设备信息
        logger.info("deviceRegisterRequest is {}", Jack.toJson(deviceRegisterRequest));
        return null;
    }

    @Override
    public CmdDirective getDirective() {
        return CommandDirective.DEVICE_REGISTER;
    }

}
