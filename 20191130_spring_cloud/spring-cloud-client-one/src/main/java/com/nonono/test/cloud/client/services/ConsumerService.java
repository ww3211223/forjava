package com.nonono.test.cloud.client.services;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class ConsumerService {

    @Autowired
    RestTemplate restTemplate;

    public String consumer() {
        return restTemplate.getForEntity("http://nonono-cloud-service/test/", String.class).getBody();
    }

    @HystrixCollapser(batchMethod = "findByIds", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")})
    public Future<String> findById(Integer id) {
        //return restTemplate.getForEntity(String.format("http://nonono-cloud-service/test/%s", id), String.class).getBody();
        return null;
    }

    @HystrixCommand
    public List<String> findByIds(List<Integer> ids) {
        String body = restTemplate.postForEntity("http://nonono-cloud-service/test/list", ids, String.class).getBody();
        return JSON.parseArray(body, String.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public String hasFallbackTest() {
        return restTemplate.getForEntity("http://nonono-cloud-service/test/fallback", String.class).getBody();
    }

    public String fallback() {
        return "local-fallback";
    }

}
