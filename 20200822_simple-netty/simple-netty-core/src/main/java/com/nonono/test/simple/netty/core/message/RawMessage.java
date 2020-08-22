package com.nonono.test.simple.netty.core.message;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawMessage {

    /**
     * 总长度
     */
    private int totalLength;// 4 - bytes

    /**
     * 魔数
     */
    private int magic;// 4 - bytes

    /**
     * 发生时间毫秒数
     */
    private long occurMills;// 8 - bytes

    /**
     * 客户端编号
     */
    private int clientNo;// 4

    /**
     * 消息类型
     */
    private int messageType;// 4

    /**
     *
     */
    private int bodyLength;// 4

    /**
     * 消息体 - 字符串格式
     */
    private String bodyText;

}
