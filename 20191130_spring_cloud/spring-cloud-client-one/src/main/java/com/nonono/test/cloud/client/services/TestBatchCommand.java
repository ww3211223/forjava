package com.nonono.test.cloud.client.services;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 测试批量命令
 */
public class TestBatchCommand extends HystrixCommand<List<String>> {
    private RestTemplate restTemplate;
    private List<Integer> ids;

    public TestBatchCommand(RestTemplate restTemplate, List<Integer> ids) {
        super(HystrixCommandGroupKey.Factory.asKey("testCommand"));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected List<String> run() throws Exception {
        System.out.println("TestBatchCommand run ids#" + ids);
        String body = restTemplate.postForEntity("http://nonono-cloud-service/test/list", ids, String.class).getBody();
        return JSON.parseArray(body, String.class);
    }
}
