package com.nonono.test.akka_test.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象消息信息
 */
public abstract class ActorMessage {

    /**
     * 消息
     */
    public String message;

}
