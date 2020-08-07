package com.nonono.test.cloud.client.services;

import com.nonono.test.cloud.client.model.User;
import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.stereotype.Component;

@Component
public class FeignFallbackService implements FeignService {

    @Override
    public String test1(String name) {
        return "test1 fallback";
    }

    @Override
    public String test2(String name, Integer age) {
        return "test2 fallback";
    }

    @Override
    public String test3(User user) {
        return "test3 fallback";
    }

    @Override
    public String test5(String name) {
        return "test5 fallback";
    }

    @Override
    public String test6(String name, Integer age) {
        return "test6 fallback";
    }

    @Override
    public String test7(Product product) {
        return "test7 fallback";
    }
}
