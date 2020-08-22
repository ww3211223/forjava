package com.nonono.test.simple.netty.core.processor;

import com.nonono.test.simple.netty.core.message.RawMessage;
import com.nonono.test.simple.netty.core.utils.Bye;

public class RawMessageEncoder {

    public RawMessage decode(byte[] data) {
        RawMessage msg = new RawMessage();
        msg.setTotalLength(Bye.readInt(data, 0));
        msg.setMagic(Bye.readInt(data, 4));
        msg.setOccurMills(Bye.readLong(data, 8));

        msg.setClientNo(Bye.readInt(data, 16));
        msg.setMessageType(Bye.readInt(data, 20));
        msg.setBodyLength(Bye.readInt(data, 24));

        msg.setBodyText(new String(Bye.readFixLength(data, 28, msg.getBodyLength())));

        return msg;
    }

    public byte[] encode(RawMessage msg) {
        byte[] data = new byte[msg.getTotalLength()];

        Bye.writeInt(data, 0, msg.getTotalLength());
        Bye.writeInt(data, 4, msg.getMagic());
        Bye.writeLong(data, 8, msg.getOccurMills());

        Bye.writeInt(data, 16, msg.getClientNo());
        Bye.writeInt(data, 20, msg.getMessageType());
        Bye.writeInt(data, 24, msg.getBodyLength());

        Bye.writeBytes(data, 28, msg.getBodyText().getBytes());
        return data;
    }

}
