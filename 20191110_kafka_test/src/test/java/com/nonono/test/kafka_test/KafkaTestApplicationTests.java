package com.nonono.test.kafka_test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class KafkaTestApplicationTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        kafkaTemplate.send("topic.quick.nononoTest", "test msg");
        Thread.sleep(5000);
    }
}
