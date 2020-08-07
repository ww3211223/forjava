package com.nonono.test.cloud.server.controller;

import com.alibaba.fastjson.JSON;
import com.nonono.test.cloud.server.model.User;
import com.nonono.test.spring.cloud.client.api.FeignServiceClient;
import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class FeignController implements FeignServiceClient {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1(@RequestParam String name) {
        throw new RuntimeException("test1");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2(@RequestParam String name, @RequestHeader Integer age) {
        return "test2 name: " + name + ",age: " + age;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public String test3(@RequestBody User user) {
        return "test3 JSON: " + JSON.toJSONString(user);
    }

    @Override
    public String test5(@RequestParam String name) {
        return "test5 name: " + name;
    }

    @Override
    public String test6(@RequestParam String name, @RequestHeader Integer age) {
        return "test6 name: " + name + ",age: " + age;
    }

    @Override
    public String test7(@RequestBody Product product) {
        return "test7 JSON: " + JSON.toJSONString(product);
    }
}
