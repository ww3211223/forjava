package com.nonono.test.simple.netty.core.message;

import com.nonono.test.ame.core.enums.CodeDescriptionFeature;
import com.nonono.test.ame.core.enums.EnumHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RawMessageType implements CodeDescriptionFeature {

    JSON_COMMAND(10002, "消息Data格式为JSON, 且为命令模式"),

    PING_COMMAND(10101, "消息Data格式为文本, 且为ping模式"),

    PONG_COMMAND(10102, "消息Data格式为文本, 且为pong模式"),

    ;

    private int code;

    private String description;

    public static RawMessageType valueOf(Integer code) {
        return EnumHelper.valueOf(code, RawMessageType.class);
    }

}
