package com.nonono.test.aop;

import java.lang.reflect.Method;

public class AfterAdvice implements Advice {
    private Object bean;

    private MethodInvocation methodInvocation;

    public AfterAdvice(Object bean, MethodInvocation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = method.invoke(bean, args);
        methodInvocation.invoke();
        return obj;
    }
}
