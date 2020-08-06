package com.nonono.test.cloud.client.controller;

import com.nonono.test.cloud.client.model.User;
import com.nonono.test.cloud.client.services.FeignService;
import com.nonono.test.spring.cloud.client.api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;


    @RequestMapping(value = "/feign/test1", method = RequestMethod.GET)
    public String feignTest1(@RequestParam String name) {
        return feignService.test1(name);
    }

    @RequestMapping(value = "/feign/test2", method = RequestMethod.GET)
    public String feignTest2(@RequestParam String name, @RequestParam Integer age) {
        return feignService.test2(name, age);
    }

    @RequestMapping(value = "/feign/test3", method = RequestMethod.GET)
    public String feignTest3(@RequestParam String name, @RequestParam Integer age) {
        User user = new User();
        user.setAge(age);
        user.setName(name);

        return feignService.test3(user);
    }

    @RequestMapping(value = "/feign_client/test5", method = RequestMethod.GET)
    public String feignTest5(@RequestParam String name) {
        return feignService.test5(name);
    }

    @RequestMapping(value = "/feign_client/test6", method = RequestMethod.GET)
    public String feignTest6(@RequestParam String name, @RequestParam Integer age) {
        return feignService.test6(name, age);
    }

    @RequestMapping(value = "/feign_client/test7", method = RequestMethod.GET)
    public String feignTest7(@RequestParam Integer id, @RequestParam String productName, @RequestParam String address) {
        Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setAddress(address);
        return feignService.test7(product);
    }
}
