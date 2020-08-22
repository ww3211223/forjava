package com.nonono.test.simple.netty.core.socket;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;
import com.nonono.test.simple.netty.core.processor.RawMessageEncoder;
import com.nonono.test.simple.netty.core.utils.Bye;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class RawMessageInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RawMessageInboundHandler.class);

    private static final int LENGTH_HEADER = 8;

    private RawMessageEncoder messageEncoder;

    public RawMessageInboundHandler(RawMessageEncoder messageEncoder) {
        this.messageEncoder = messageEncoder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            logger.info("inbound ignore msg#{}", msg);
            return;
        }

        ctx.channel().id().asShortText();

        long s1 = System.currentTimeMillis();

        ByteBuf in = (ByteBuf) msg;
        in.resetReaderIndex();

        int ava = in.readableBytes();
        logger.debug("begin decode in bytes length - {}, ctx - {}", ava, System.identityHashCode(ctx));

        // 1. Wait until the length prefix is available.
        if (ava < LENGTH_HEADER) {
            in.resetReaderIndex();
            return;
        }

        // 2. check total length
        int totalLength = in.readInt();
        if (ava < totalLength) {
            in.resetReaderIndex();
            logger.warn("mismatch totalLength is {}, ava is {}", totalLength, ava);
            return;
        }

        // 3. check magic number
        long magic = in.readUnsignedInt();
        if (magic != Bye.MAGIC_NUMBER) {
            in.resetReaderIndex();
            logger.warn("mismatch magic is {}", magic);
            return;
        }

        in.resetReaderIndex();

        try {
            byte[] data = new byte[totalLength];
            in.readBytes(data);
            String str = new String(data);

            RawMessage raw = messageEncoder.decode(data);

            // 非 ping消息, 才打印debug日志
            if (raw.getMessageType() != RawMessageType.PING_COMMAND.getCode()) {
                logger.debug("客户端消息 len={}, 文本 \n{}", str.length(), str);
                logger.debug("客户端消息 len={}, 格式化十六进制 \n{}", data.length, Bye.formatPrettyBytes(data));
                logger.debug("客户端消息 len={}, 十六进制 \n{}", data.length, Hex.encodeHexString(data, true));
                logger.debug("客户端消息 len={}, type={}, dec is {}", data.length, raw.getMessageType(), raw.getBodyText());
            }

            ctx.writeAndFlush(raw);
        } catch (Exception e) {
            logger.error("inbound error", e);
            throw new RuntimeException("", e);
        } finally {
            Bye.release(in);
        }

        logger.debug("inbound channel handle, elapsed {} ms",  System.currentTimeMillis() - s1);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // logger.debug("channel read complete - {}", System.identityHashCode(ctx.channel()));
        ctx.flush();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel active - {}", ctx.channel());
        // TODO:
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO:
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("context#{} exceptionCaught", ctx, cause);
        ctx.close();

        // TODO:
    }


}
