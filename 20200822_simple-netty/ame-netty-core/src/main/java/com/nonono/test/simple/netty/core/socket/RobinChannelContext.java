package com.nonono.test.simple.netty.core.socket;

import com.nonono.test.simple.netty.core.utils.Bye;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RobinChannelContext {

    private String uniqueId;

    private String channelId;

    private ChannelHandlerContext context;

    private LocalDateTime connectTime;

    public RobinChannelContext(ChannelHandlerContext context) {
        this.context = context;
        this.channelId = Bye.getChannelId(context);
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setConnectTime(LocalDateTime connectTime) {
        this.connectTime = connectTime;
    }
}
