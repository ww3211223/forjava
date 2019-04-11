package com.nonono.test.akka_test.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkService {

    /**
     * 处理逻辑
     */
    public void work() {
        log.info("Hello World.");
    }

}
