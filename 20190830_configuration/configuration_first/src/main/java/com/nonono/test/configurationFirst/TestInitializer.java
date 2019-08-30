package com.nonono.test.configurationFirst;

import com.nonono.test.configurationSecond.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestInitializer implements ApplicationRunner {

    @Autowired
    private TestService testService;

    @Autowired
    private TestModel testModel;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String testValue = testService.getTestValue();
        System.out.println("testValue:" + testValue);
        System.out.println("testModel:" + testModel.getId() + "," + testModel.getName());
    }
}
