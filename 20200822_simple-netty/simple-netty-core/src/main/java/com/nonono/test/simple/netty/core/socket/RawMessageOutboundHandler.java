package com.nonono.test.simple.netty.core.socket;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.processor.RawMessageChannelProcessor;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.processor.RawMessageFactory;
import com.nonono.test.simple.netty.core.processor.RawMessageProcessor;
import com.nonono.test.simple.netty.core.utils.Bye;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RawMessageOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RawMessageOutboundHandler.class);

    private RawMessageEncoder messageEncoder;

    public RawMessageOutboundHandler(RawMessageEncoder messageEncoder) {
        this.messageEncoder = messageEncoder;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof RawMessage)) {
            logger.info("outbound not-process msg#{}", msg);
            ctx.writeAndFlush(msg);
            return;
        }

        long s1 = System.currentTimeMillis();

        RawMessage req = (RawMessage) msg;

        RawMessageProcessor processor = RawMessageFactory.getProcessor(RawMessageType.valueOf(req.getMessageType()));
        if (processor == null) {
            logger.error("no processor for messageType#{}, body is {}", req.getMessageType(), req.getBodyText());
            return;
        }

        ByteBuf outBuf = null;
        try {
            RawMessage response = null;
            try {
                if (processor instanceof RawMessageChannelProcessor) {
                    response = ((RawMessageChannelProcessor) processor).process(ctx, req);
                } else {
                    response = processor.process(req);
                }
            } catch (Exception e) {
                logger.error("processor do process error", e);
            }

            byte[] bytesData = null;
            if (response != null && (bytesData = messageEncoder.encode(response)) != null) {
                outBuf = Bye.wrapBytes(ctx, bytesData);
                ctx.writeAndFlush(outBuf);
                logger.debug("write response bytes#{}", bytesData.length);
            } else {
                logger.debug("no response for request#{}, processor is {}, response is {]", req.getMessageType(), processor, response);
            }

        } catch (Exception e) {
            logger.error("outbound error", e);
            throw new RuntimeException("", e);
        } finally {
            // K.release(outBuf);
        }

        logger.debug("end encodeText, body length#{}, elapsed {} m-sec", req.getBodyLength(), System.currentTimeMillis() - s1);
    }
}
