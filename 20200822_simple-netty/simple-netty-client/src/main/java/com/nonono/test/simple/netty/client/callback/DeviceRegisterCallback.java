package com.nonono.test.simple.netty.client.callback;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.core.command.BaseCommand;
import com.nonono.test.simple.netty.core.command.BaseCommandDirective;
import com.nonono.test.simple.netty.core.dto.ClientRegisterCallbackRequest;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeviceRegisterCallback extends BaseCommand<ClientRegisterCallbackRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DeviceRegisterCallback.class);

    @Override
    protected JsonCommand doExecute(ChannelHandlerContext ctx, int clientNo, ClientRegisterCallbackRequest callback) {
        logger.info("收到回调消息 {}", Jack.toJson(callback));
        return null;
    }

    @Override
    public CmdDirective getDirective() {
        return BaseCommandDirective.RESP_SERVER_ISSUANCE;
    }

}
