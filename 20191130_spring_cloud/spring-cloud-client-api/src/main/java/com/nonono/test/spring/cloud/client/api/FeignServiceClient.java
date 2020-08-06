package com.nonono.test.spring.cloud.client.api;

import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/feign")
public interface FeignServiceClient {

    @RequestMapping(value = "/test5", method = RequestMethod.GET)
    String test5(@RequestParam("name") String name);

    @RequestMapping(value = "/test6", method = RequestMethod.GET)
    String test6(@RequestParam("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/test7", method = RequestMethod.POST)
    String test7(@RequestBody Product product);
}
