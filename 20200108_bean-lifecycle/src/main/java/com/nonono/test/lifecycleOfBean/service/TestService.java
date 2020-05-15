package com.nonono.test.lifecycleOfBean.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 测试服务
 */
public class TestService implements InitializingBean, DisposableBean, ApplicationContextAware, BeanFactoryAware {

    @PostConstruct
    public void initPostConstruct() {
        System.out.println("TestService.@PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("TestService.@PreDestroy");
    }

    /**
     * 销毁
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("TestService DisposableBean.destroy()");
    }

    /**
     * set属性后
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestService InitializingBean.afterPropertiesSet()");
    }

    public void destroyMethod() throws Exception {
        System.out.println("TestService.destroyMethod()");
    }

    public void initMethod() throws Exception {
        System.out.println("TestService.initMethod()");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("TestService.setBeanFactory()");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("TestService.setApplicationContext()");
    }
}
