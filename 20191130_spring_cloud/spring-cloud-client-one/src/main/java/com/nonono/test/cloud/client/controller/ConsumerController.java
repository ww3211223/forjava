package com.nonono.test.cloud.client.controller;

import com.nonono.test.cloud.client.services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String consumer() {
        return consumerService.consumer();
    }

    @RequestMapping(value = "/has-fallback", method = RequestMethod.GET)
    public String hasFallbackTest() {
        return consumerService.hasFallbackTest();
    }
}
