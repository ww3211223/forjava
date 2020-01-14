package com.nonono.test.lifecycleOfBean.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestService) {
            System.out.println(String.format("CustomBeanPostProcessor.postProcessBeforeInitialization()#beanName:%s", beanName));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestService) {
            System.out.println(String.format("CustomBeanPostProcessor.postProcessAfterInitialization()#beanName:%s", beanName));
        }
        return bean;
    }
}
