package com.nonono.test.simple.netty.client.model;

import com.nonono.test.ame.core.enums.EnumHelper;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.ame.core.message.CmdDirectiveFactory;
import lombok.Getter;

@Getter
public enum CommandDirective implements CmdDirective {

    NONE(0, "表示null"),

    DEVICE_REGISTER(60000, "客户端注册 - 请求"),
    RESP_DEVICE_REGISTER(70000, "客户端注册 - 响应"),

    SERVER_CTRL_DEVICE(60001, "服务端控制设备 - 请求"),
    RESP_SERVER_CTRL_DEVICE(70001, "服务端控制设备 - 响应"),

    ;

    private int code;

    private String description;

    CommandDirective(int code, String description) {
        this.code = code;
        this.description = description;
        CmdDirectiveFactory.register(this);
    }

    public static CommandDirective valueOf(Integer code) {
        return EnumHelper.valueOf(code, CommandDirective.class);
    }

}
