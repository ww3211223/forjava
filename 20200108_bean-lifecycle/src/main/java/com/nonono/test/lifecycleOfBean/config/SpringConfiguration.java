package com.nonono.test.lifecycleOfBean.config;

import com.nonono.test.lifecycleOfBean.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    TestService testService() {
        return new TestService();
    }
}
