package com.nonono.test.confiImport;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;


public class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);

//        AnnotationAttributes enableAJAutoProxy = AnnotationConfigUtils.attributesFor(annotationMetadata, EnableAspectJAutoProxy.class);
//        if (enableAJAutoProxy.getBoolean("proxyTargetClass")) {
//            AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
//        }
    }
}
