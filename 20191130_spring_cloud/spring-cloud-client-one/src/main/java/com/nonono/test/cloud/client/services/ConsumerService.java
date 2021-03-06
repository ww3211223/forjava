package com.nonono.test.cloud.client.services;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.nonono.test.cloud.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class ConsumerService {

    @Autowired
    RestTemplate restTemplate;

    private int count = 0;

    public String consumer() {
        return restTemplate.getForEntity("http://nonono-cloud-service/test/", String.class).getBody();
    }

    @HystrixCollapser(batchMethod = "findByIds", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "500")})
    public Future<String> findById(Integer id) {
        //return restTemplate.getForEntity(String.format("http://nonono-cloud-service/test/%s", id), String.class).getBody();
        return null;
    }

    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public List<String> findByIds(List<Integer> ids) {
        System.out.println("findByIds ids#" + ids);
        String body = restTemplate.postForEntity("http://nonono-cloud-service/test/list", ids, String.class).getBody();
        return JSON.parseArray(body, String.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public String hasFallbackTest() {

        count++;
        System.out.println("count: " + count);
        if (count % 3 != 0) {
            System.out.println("测试抛出异常。");
            throw new RuntimeException("test Exception.");
        }

        System.out.println("调用service.");
        return restTemplate.getForEntity("http://nonono-cloud-service/test/fallback", String.class).getBody();
    }

    public String fallback() {
        return "local-fallback";
    }
}
