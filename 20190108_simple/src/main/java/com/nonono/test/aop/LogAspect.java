package com.nonono.test.aop;

import com.nonono.test.aop.Action;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.nonono.test.aop.Action)")
    public void annotaionPointCut() {
    }

    @After("annotaionPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截：" + action.name());
    }

    @Before("execution(* com.nonono.test.aop.DemoMethodService.*(..))")
    public void before(JoinPoint joinpoint) {
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截：" + method.getName());
    }
}
