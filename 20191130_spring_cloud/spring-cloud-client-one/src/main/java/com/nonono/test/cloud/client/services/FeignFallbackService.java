package com.nonono.test.cloud.client.services;

import com.nonono.test.cloud.client.model.User;
import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RequestMapping("/fallback")
public class FeignFallbackService implements FeignApiService {
    @Override
    public String test(@RequestParam("name") String name) {
        return "test5 fallback";
    }

    @Override
    public String test(@RequestParam("name") String name, @RequestHeader("age") Integer age) {
        return "test6 fallback";
    }

    @Override
    public String test(@RequestBody Product product) {
        return "test7 fallback";
    }
}
