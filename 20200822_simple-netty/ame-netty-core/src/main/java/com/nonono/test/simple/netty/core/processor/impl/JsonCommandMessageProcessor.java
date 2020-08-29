package com.nonono.test.simple.netty.core.processor.impl;

import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.simple.netty.core.command.CommandProcessorFactory;
import com.nonono.test.simple.netty.core.command.ICommand;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.processor.BaseRawMessageChannelProcessor;
import com.nonono.test.simple.netty.core.utils.RawMessages;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonCommandMessageProcessor extends BaseRawMessageChannelProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonCommandMessageProcessor.class);

    @Override
    public RawMessage process(ChannelHandlerContext ctx, RawMessage msg) {
        JsonCommand commandReq = RawMessages.parseJsonCommand(msg.getClientNo(), msg.getBodyText());

        ICommand command = CommandProcessorFactory.getCommand(commandReq.getDirective());
        if (command == null) {
            logger.warn("JsonCommand#{} has not processor.", commandReq.getDirective().getCode());
            return null;
        }

        JsonCommand commandResp = command.execute(ctx, commandReq);
        if (commandResp == null) {// no need to response
            return null;
        }

        commandResp.setClientNo(msg.getClientNo());
        commandResp.setRequestId(commandReq.getRequestId());

        return RawMessages.buildJsonMessage(msg.getClientNo(), Jack.toJson(commandResp));
    }

    @Override
    public RawMessageType messageType() {
        return RawMessageType.JSON_COMMAND;
    }
}
