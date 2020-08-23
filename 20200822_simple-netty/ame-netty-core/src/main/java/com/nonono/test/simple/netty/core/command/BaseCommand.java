package com.nonono.test.simple.netty.core.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nonono.test.ame.core.json.Jack;
import com.nonono.test.ame.core.utils.ClassUtil;
import com.nonono.test.simple.netty.core.common.CustomEnumDeserializers;
import com.nonono.test.simple.netty.core.message.JsonCommand;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public abstract class BaseCommand<REQ> implements ICommand, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);

    private Class<REQ> reqClass;

    private static ObjectMapper mapper;

    static {
        mapper = Jack.createMapper();

        SimpleModule enumModule = new SimpleModule();
        enumModule.setDeserializers(new CustomEnumDeserializers());
        mapper.registerModule(enumModule);

    }

    public BaseCommand() {
        reqClass = ClassUtil.getSuperClassGenericType(getClass(), 0);
    }

    @Override
    public JsonCommand execute(ChannelHandlerContext ctx, JsonCommand comm) {
        REQ req = null;
        try {
            if (CharSequence.class.isAssignableFrom(reqClass)) {
                req = (REQ) comm.getData();
            } else {
                logger.info("comm.getData() is {}", comm.getData());
                req = mapper.readValue(comm.getData(), reqClass);
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        // REQ req = Jack.toObj(comm.getData(), reqClass);
        return doExecute(ctx, comm.getClientNo(), req);
    }

    /**
     * @param ctx
     * @param clintNo
     * @param req
     * @return
     */
    protected abstract JsonCommand doExecute(ChannelHandlerContext ctx, int clintNo, REQ req);

    @Override
    public void afterPropertiesSet() throws Exception {
        CommandProcessorFactory.register(this);
    }
}
