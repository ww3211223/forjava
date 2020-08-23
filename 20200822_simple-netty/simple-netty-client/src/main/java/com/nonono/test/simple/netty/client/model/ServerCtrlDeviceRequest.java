package com.nonono.test.simple.netty.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务控制设备请求
 */
@Getter
@Setter
public class ServerCtrlDeviceRequest {

    /**
     * 指令
     */
    private String instruct;

}
