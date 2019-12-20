package com.nonono.test.rabbitmq.customer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "nonono.test.q", containerFactory = "listenerContainerFactory")
    public void processMessage(String msg) {
        System.out.println("processMessage 收到队列消息：" + msg);
    }

    @RabbitListener(queues = "nonono.test.q2", containerFactory = "listenerContainerFactory2")
    public void processMessage2(String msg) {
        System.out.println("processMessage2 收到队列消息：" + msg);
    }
}
