package com.nonono.test.simple.netty.core.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nonono.test.ame.core.message.CmdDirective;
import com.nonono.test.ame.core.message.CmdDirectiveFactory;
import com.nonono.test.simple.netty.core.common.RequestSequence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonCommand {

    /**
     * 请求Id
     */
    private long requestId;

    /**
     * 客户端Id
     */
    private int clientNo;

    /**
     * 命令值
     */
    private int directiveVal;

    /**
     * 数据
     */
    private String data;

    /**
     * 状态
     */
    private int status;

    /**
     * 消息
     */
    private String message;

    public JsonCommand() {

    }

    public JsonCommand(CmdDirective directive, int status, String data, String message) {
        setDirective(directive);
        this.status = status;
        this.data = data;
        this.message = message;
        this.requestId = RequestSequence.next();
    }

    @JsonIgnore
    public CmdDirective getDirective() {
        return CmdDirectiveFactory.get(directiveVal);
    }

    @JsonIgnore
    public void setDirective(CmdDirective directive) {
        this.directiveVal = directive.getCode();
    }

}
