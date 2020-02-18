package com.nonono.test.mybaits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements ApplicationRunner {
    @Autowired
    private IProductDao productDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ProductModel productModel = productDao.getProducts(1);
        System.out.println("TestJob productName: " + productModel.getName());
    }
}
