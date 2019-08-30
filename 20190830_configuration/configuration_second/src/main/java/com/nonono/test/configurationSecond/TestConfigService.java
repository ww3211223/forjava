package com.nonono.test.configurationSecond;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestConfigService {

    @Value("${test.value}")
    private String testValue;

    public String getTestValue() {
        return testValue;
    }
}
