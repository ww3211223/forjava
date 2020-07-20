package com.nonono.test.cloud.client.services;

import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试请求合并命令
 */
public class TestCollapseCommand extends HystrixCollapser<List<String>, String, Integer> {
    private RestTemplate restTemplate;
    private Integer id;

    public TestCollapseCommand(RestTemplate restTemplate, Integer id) {
        super(Setter.withCollapserKey(
                HystrixCollapserKey.Factory.asKey("testCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(1000)));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    public Integer getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        List<Integer> ids = Lists.newArrayListWithCapacity(collapsedRequests.size());
        ids.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new TestBatchCommand(restTemplate, ids);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> collapsedRequest : collapsedRequests) {
            String message = batchResponse.get(count++);
            collapsedRequest.setResponse(message);
        }
    }
}
