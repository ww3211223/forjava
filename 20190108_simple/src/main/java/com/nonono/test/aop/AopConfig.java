package com.nonono.test.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.nonono.test.aop")
@EnableAspectJAutoProxy
public class AopConfig {

}
