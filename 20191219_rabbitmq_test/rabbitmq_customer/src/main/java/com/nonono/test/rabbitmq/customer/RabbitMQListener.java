package com.nonono.test.rabbitmq.customer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "nonono.test.q", containerFactory = "listenerContainerFactory", ackMode = "MANUAL")
    public void processMessage(@Payload String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("processMessage 收到队列消息：" + msg);
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = "nonono.test.q2", containerFactory = "listenerContainerFactory2")
    public void processMessage2(String msg) {
        System.out.println("processMessage2 收到队列消息：" + msg);
    }
}
