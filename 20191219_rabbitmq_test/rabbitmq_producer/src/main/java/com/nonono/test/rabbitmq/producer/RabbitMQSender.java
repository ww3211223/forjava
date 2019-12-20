package com.nonono.test.rabbitmq.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * rabbit mq消息发送组件
 */
@Component
public class RabbitMQSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送成功：" + correlationData);
        } else {
            System.out.println("消息发送失败：" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("发送失败：" + message.getMessageProperties().getCorrelationId());
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void send(String exchange, String routingKey, String msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("开始发送消息：" + correlationId);
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationId);
        System.out.println("结束发送消息：" + msg.toLowerCase());
    }
}
