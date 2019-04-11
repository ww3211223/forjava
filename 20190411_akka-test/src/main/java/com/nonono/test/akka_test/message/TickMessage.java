package com.nonono.test.akka_test.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 心跳消息
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TickMessage extends ActorMessage {

    /**
     * 默认Key
     */
    public static String DEFAULT_KEY = "TICK_KEY";
}
