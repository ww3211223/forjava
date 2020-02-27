package com.nonono.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements ApplicationRunner {

    @Autowired
    private TestRedis testRedis;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        testRedis.testStr();
//        testRedis.testObj();
//        testRedis.testIncr();
        testRedis.testRedisLock();
    }
}
