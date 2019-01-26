package com.nonono.test.confiImport;


public @interface EnableAspectJAutoProxy {
    boolean proxyTargetClass() default false;
}
