package com.nonono.test.akka_test.di;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * Spring Actor生成器
 */
public class SpringActorProducer implements IndirectActorProducer {

    /**
     * Spring Application Context
     */
    private ApplicationContext applicationContext;

    /**
     * 生产类型
     */
    private Class<? extends Actor> clazz;

    /**
     * 构造函数参数
     */
    private Object[] args;

    /**
     * 生成器构造函数
     *
     * @param context
     * @param clazz
     * @param args
     */
    public SpringActorProducer(ApplicationContext context, Class<? extends Actor> clazz, Object... args) {
        this.applicationContext = context;
        this.clazz = clazz;
        this.args = args;
    }

    @Override
    public Actor produce() {
        return this.applicationContext.getBean(clazz, args);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return this.clazz;
    }
}
