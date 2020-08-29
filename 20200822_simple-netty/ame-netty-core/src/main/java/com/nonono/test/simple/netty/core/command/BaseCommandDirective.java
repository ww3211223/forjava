package com.nonono.test.simple.netty.core.command;

import com.nonono.test.ame.core.enums.EnumHelper;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.ame.core.message.CmdDirectiveFactory;
import lombok.Getter;

@Getter
public enum BaseCommandDirective implements CmdDirective {

    NONE(0, "表示null"),

    CLIENT_REGISTER(60000, "客户端注册 - 请求"),
    RESP_CLIENT_REGISTER(70000, "客户端注册 - 响应"),

    SERVER_ISSUANCE(60001, "服务端指令下发 - 请求"),
    RESP_SERVER_ISSUANCE(70001, "服务端指令下发 - 响应"),

    ;

    private int code;

    private String description;

    BaseCommandDirective(int code, String description) {
        this.code = code;
        this.description = description;
        CmdDirectiveFactory.register(this);
    }

    public static BaseCommandDirective valueOf(Integer code) {
        return EnumHelper.valueOf(code, BaseCommandDirective.class);
    }

}
