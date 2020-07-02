package com.nonono.test.cloud.client.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

    @Autowired
    RestTemplate restTemplate;

    public String consumer() {
        return restTemplate.getForEntity("http://nonono-cloud-service/test/", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public String hasFallbackTest() {
        return restTemplate.getForEntity("http://nonono-cloud-service/test/fallback", String.class).getBody();
    }

    public String fallback() {
        return "local-fallback";
    }

}
