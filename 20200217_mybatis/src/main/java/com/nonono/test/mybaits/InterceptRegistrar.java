package com.nonono.test.mybaits;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * sql拦截器注册器
 */
public class InterceptRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition bd = BeanDefinitionBuilder.genericBeanDefinition(ExecutorIntercept.class).getBeanDefinition();
        registry.registerBeanDefinition(BeanDefinitionReaderUtils.generateBeanName(bd, registry), bd);
    }
}
