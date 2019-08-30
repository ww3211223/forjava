package com.nonono.test.configurationSecond;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "test.config")
public class TestModel {

    private Integer id;

    private String name;
}
