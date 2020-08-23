package com.nonono.test.simple.netty.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * byte帮助类
 */
public class Bye {

    private static final Logger logger = LoggerFactory.getLogger(Bye.class);

    /**
     * 魔术数字
     */
    public static final int MAGIC_NUMBER = 2145445700;


    /**
     * 写入long值
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void writeLong(byte[] data, int offset, long value) {
        data[offset] = (byte) ((value >> 56) & 0xFF);
        data[offset + 1] = (byte) ((value >> 48) & 0xFF);
        data[offset + 2] = (byte) ((value >> 40) & 0xFF);
        data[offset + 3] = (byte) ((value >> 32) & 0xFF);
        data[offset + 4] = (byte) ((value >> 24) & 0xFF);
        data[offset + 5] = (byte) ((value >> 16) & 0xFF);
        data[offset + 6] = (byte) ((value >> 8) & 0xFF);
        data[offset + 7] = (byte) ((value) & 0xFF);
    }

    /**
     * 写入int值
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void writeInt(byte[] data, int offset, int value) {
        data[offset] = (byte) ((value >> 24) & 0xFF);
        data[offset + 1] = (byte) ((value >> 16) & 0xFF);
        data[offset + 2] = (byte) ((value >> 8) & 0xFF);
        data[offset + 3] = (byte) ((value) & 0xFF);
    }

    /**
     * 写入byte值
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void writeByte(byte[] data, int offset, byte value) {
        data[offset] = value;
    }

    /**
     * 写入byte数组
     *
     * @param data
     * @param offset
     * @param bs
     */
    public static void writeBytes(byte[] data, int offset, byte[] bs) {
        for (int i = 0; i < bs.length; i++) {
            data[offset + i] = bs[i];
        }
    }

    /**
     * 读取单个byte
     *
     * @param bs
     * @param offset
     * @return
     */
    public static byte read1Byte(byte[] bs, int offset) {
        return bs[offset];
    }

    /**
     * 读取4个bytes
     *
     * @param bs
     * @param offset
     * @return
     */
    public static byte[] read4Bytes(byte[] bs, int offset) {
        return new byte[]{bs[offset], bs[offset + 1], bs[offset + 2], bs[offset + 3]};
    }

    /**
     * 读取固定长度byte值
     *
     * @param bs
     * @param offset
     * @param len
     * @return
     */
    public static byte[] readFixLength(byte[] bs, int offset, int len) {
        byte[] tar = new byte[len];
        System.arraycopy(bs, offset, tar, 0, len);
        return tar;
    }

    /**
     * 读取单个int值
     *
     * @param bs
     * @param offset
     * @return
     */
    public static int readInt(byte[] bs, int offset) {
        byte[] seg = read4Bytes(bs, offset);
        int ret = ((int) seg[0] & 0xFF) << 24 |
                ((int) seg[1] & 0xFF) << 16 |
                ((int) seg[2] & 0xFF) << 8 |
                ((int) seg[3] & 0xFF);
        return ret;
    }

    /**
     * 读取单个long值
     *
     * @param bs
     * @param offset
     * @return
     */
    public static long readLong(byte[] bs, int offset) {
        byte[] seg = readFixLength(bs, offset, 8);

        long ret = ((long) seg[0] & 0xFF) << 56 |
                ((long) seg[1] & 0xFF) << 48 |
                ((long) seg[2] & 0xFF) << 40 |
                ((long) seg[3] & 0xFF) << 32 |
                ((long) seg[4] & 0xFF) << 24 |
                ((long) seg[5] & 0xFF) << 16 |
                ((long) seg[6] & 0xFF) << 8 |
                ((long) seg[7] & 0xFF);
        return ret;
    }

    /**
     * 格式化
     *
     * @param bytes
     * @return
     */
    public static String formatPrettyBytes(byte[] bytes) {
        int group = bytes.length / 16;
        group = bytes.length % 16 == 0 ? group : group + 1;
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for (int i = 0; i < group; i++) {
            for (int j = 0; j < 16; j++) {
                int offset = i * 16 + j;
                if (offset >= bytes.length) {
                    break;
                }
                try {
                    sb.append(String.format("%02x ", bytes[offset] & 0xff));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static ByteBuf wrapBytes(ChannelHandlerContext ctx, byte[] data) {
        ByteBuf buf = ctx.alloc().heapBuffer();
        buf.writeBytes(data);
        return buf;
    }

    /**
     * 释放资源
     *
     * @param buf
     * @return
     */
    public static boolean release(ByteBuf buf) {
        try {
            return ReferenceCountUtil.release(buf);
        } catch (Exception e) {
            logger.error("release buf error", e);
        }

        return false;
    }

    /**
     * @param ctx
     * @return
     */
    public static String getChannelId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

    /**
     * 获取字符长度
     *
     * @param s
     * @return
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;
        }
        return length;
    }

}
