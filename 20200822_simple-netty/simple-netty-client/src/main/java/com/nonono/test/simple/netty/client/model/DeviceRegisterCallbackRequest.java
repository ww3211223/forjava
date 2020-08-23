package com.nonono.test.simple.netty.client.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRegisterCallbackRequest {

    private String deviceId;
    private String message;
}
