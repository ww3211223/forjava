package com.nonono.test.configurationFirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        List<PropertySource<?>> propertySources = this.loadSources();
        for (PropertySource<?> item : propertySources) {
            environment.getPropertySources().addLast(item);
        }
    }

    private List<PropertySource<?>> loadSources() {
        String path = "classpath:/test/test2.properties";
        Resource resource = new DefaultResourceLoader().getResource(path);
        if (resource != null && resource.exists()) {
            try {
                return new PropertiesPropertySourceLoader().load("val", resource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
