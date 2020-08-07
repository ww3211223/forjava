package com.nonono.test.cloud.server.one.controller;

import com.alibaba.fastjson.JSON;
import com.nonono.test.cloud.server.one.model.User;
import com.nonono.test.spring.cloud.client.api.FeignServiceClient;
import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class FeignController implements FeignServiceClient {

    @Value("${server.name}")
    private String serverName;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1(@RequestParam String name) {
        throw new RuntimeException(serverName + " test1 exception");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2(@RequestParam String name, @RequestHeader Integer age) {
        return serverName + " test2 name: " + name + "age: " + age;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public String test3(@RequestBody User user) {
        return serverName + " test3 JSON: " + JSON.toJSONString(user);
    }

    @Override
    public String test(@RequestParam String name) {
        throw new RuntimeException(serverName + " test5 exception");
    }

    @Override
    public String test(@RequestParam String name, @RequestHeader Integer age) {
        return serverName + " test6 name: " + name + ",age: " + age;
    }

    @Override
    public String test(@RequestBody Product product) {
        return serverName + " test7 JSON: " + JSON.toJSONString(product);
    }
}
