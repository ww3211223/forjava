package com.nonono.test._springboot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "author")
@Getter
@Setter
public class AuthorSettings {
    private String name;
    private Integer age;
}
