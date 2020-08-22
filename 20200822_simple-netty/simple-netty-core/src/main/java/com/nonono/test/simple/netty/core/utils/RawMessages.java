package com.nonono.test.simple.netty.core.utils;

import com.nonono.test.simple.netty.core.message.JsonCommand;
import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.message.RawMessageType;

/**
 * 原始消息工具类
 */
public class RawMessages {

    public static RawMessage buildJsonMessage(int clientNo, String body) {
        return from(RawMessageType.JSON_COMMAND, clientNo, body);
    }

    public static RawMessage buildPingMessage(int clientNo) {
        return from(RawMessageType.PING_COMMAND, clientNo, "ping");
    }

    public static RawMessage from(RawMessageType msgType, int clientNo, String body) {
        return from(msgType.getCode(), clientNo, body);
    }

    public static RawMessage from(int messageType, int clientNo, String body) {
        RawMessage msg = new RawMessage();

        int bLen = body.length();
        int tLen = 4 + 4 + 8 + 4 + 4 + 4 + bLen;

        msg.setTotalLength(tLen);
        msg.setMagic(Bye.MAGIC_NUMBER);
        msg.setOccurMills(System.currentTimeMillis());

        msg.setClientNo(clientNo);
        msg.setMessageType(messageType);
        msg.setBodyLength(bLen);

        msg.setBodyText(body);

        return msg;
    }

    public static JsonCommand parseJsonCommand(int clientNo, String text) {
        JsonCommand comm = Jack.toObj(text, JsonCommand.class);
        if (clientNo > -1) {
            comm.setClientNo(clientNo);
        }

        return comm;
    }

}
