package com.nonono.test.cloud.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = Logger.getLogger(TestController.class.getName());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("test host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "test";
    }
}
