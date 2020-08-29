package com.nonono.test.simple.netty.core.command;

import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.simple.netty.core.dto.ClientRegisterRequest;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.server.ChannelHandlerFactory;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class BaseClientRegisterCommand extends BaseCommand<ClientRegisterRequest> {

    private static final Logger logger = LoggerFactory.getLogger(BaseClientRegisterCommand.class);

    private int socketClientNo;

    public BaseClientRegisterCommand(int socketClientNo) {
        this.socketClientNo = socketClientNo;
    }

    @Override
    protected JsonCommand doExecute(ChannelHandlerContext ctx, int clientNo, ClientRegisterRequest deviceRegisterRequest) {
        String message;
        if (Objects.isNull(deviceRegisterRequest)) {
            message = "register fail.Request is null.";
        } else {
            ChannelHandlerFactory.register(deviceRegisterRequest.getClientIdentify(), ctx);
            message = "client register complete.";
        }
        JsonCommand command = new JsonCommand();
        command.setDirective(BaseCommandDirective.RESP_CLIENT_REGISTER);
        command.setRequestId(0L);
        command.setClientNo(socketClientNo);
        command.setDirectiveVal(BaseCommandDirective.RESP_CLIENT_REGISTER.getCode());
        command.setData(StringUtils.EMPTY);
        command.setStatus(0);
        command.setMessage(message);

        return command;
    }

    @Override
    public CmdDirective getDirective() {
        return BaseCommandDirective.CLIENT_REGISTER;
    }

}
