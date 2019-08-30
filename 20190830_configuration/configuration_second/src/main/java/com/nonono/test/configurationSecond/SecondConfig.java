package com.nonono.test.configurationSecond;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.nonono.test.configurationSecond")
@PropertySource(value = {"classpath:test.properties"}, encoding = "utf-8")
public class SecondConfig {
}
