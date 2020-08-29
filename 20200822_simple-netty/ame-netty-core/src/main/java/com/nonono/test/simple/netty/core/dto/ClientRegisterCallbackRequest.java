package com.nonono.test.simple.netty.core.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegisterCallbackRequest {

    /**
     * 客户端标识
     */
    private String clientIdentify;

    /**
     * 消息
     */
    private String message;
}
