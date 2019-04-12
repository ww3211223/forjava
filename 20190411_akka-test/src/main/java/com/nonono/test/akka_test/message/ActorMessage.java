package com.nonono.test.akka_test.message;

import lombok.*;

/**
 * 抽象消息信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorMessage {

    /**
     * Id
     */
    private int id;

    /**
     * 消息
     */
    private String message;

}
