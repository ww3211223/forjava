package com.nonono.test.yaml;

import org.apache.commons.io.IOUtils;
import org.ho.yaml.Yaml;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
class TestApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("system running.");

        Yaml yaml = new Yaml();
        String path = "test.yaml";
        String content = "";

        try {
            InputStream input = new ClassPathResource(path).getInputStream();
            content = IOUtils.toString(input);
        } catch (Exception e) {
            throw e;
        }

        ProductModel result = yaml.loadType(content, ProductModel.class);
        System.out.println(result);
    }
}
