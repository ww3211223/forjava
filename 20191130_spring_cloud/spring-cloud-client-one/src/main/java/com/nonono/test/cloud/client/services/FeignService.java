package com.nonono.test.cloud.client.services;

import com.nonono.test.cloud.client.model.User;
import com.nonono.test.spring.cloud.client.api.FeignServiceClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("nonono-cloud-service")
public interface FeignService extends FeignServiceClient {

    @RequestMapping(value = "/feign/test1", method = RequestMethod.GET)
    String test1(@RequestParam("name") String name);

    @RequestMapping(value = "/feign/test2", method = RequestMethod.GET)
    String test2(@RequestParam("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/feign/test3", method = RequestMethod.POST)
    String test3(@RequestBody User user);
}
