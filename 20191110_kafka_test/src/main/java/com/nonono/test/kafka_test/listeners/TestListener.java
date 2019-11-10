package com.nonono.test.kafka_test.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 测试消费者
 */
@Component
@Slf4j
public class TestListener {

    @KafkaListener(topics = "hj_mkt_test")
    public void listen(String msgData) {
        log.info("收到消息：" + msgData);
    }
}
