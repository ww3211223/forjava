package com.nonono.test.configurationFirst;

import com.nonono.test.configurationSecond.TestConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class TestService {

    @Autowired
    private TestConfigService testConfigService;

    public String getTestValue() {
        return testConfigService.getTestValue();
    }

}
