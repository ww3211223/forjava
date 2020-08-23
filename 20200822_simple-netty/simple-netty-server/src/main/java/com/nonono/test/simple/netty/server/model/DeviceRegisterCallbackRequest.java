package com.nonono.test.simple.netty.server.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRegisterCallbackRequest {

    private String deviceId;
    private String message;
}
