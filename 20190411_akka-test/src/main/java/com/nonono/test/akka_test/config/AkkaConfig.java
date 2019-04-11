package com.nonono.test.akka_test.config;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.nonono.test.akka_test.constant.AkkaConsts;
import com.nonono.test.akka_test.di.SpringActorProducer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AkkaConfig implements ApplicationContextAware {

    /**
     * 系统名称
     */
    public final static String SYSTEM_NAME = "akka_test";

    /**
     * Spring 上下文对象
     */
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * AKKA
     */
    private static ActorSystem AKKA_SYSTEM;

    /**
     * 获取 AkkaSystem
     *
     * @return
     */
    public static ActorSystem getAkkaSystem() {
        return AKKA_SYSTEM;
    }

    /**
     * 获取Spring上下文对象
     *
     * @return
     */
    public static ApplicationContext getContext() {
        return APPLICATION_CONTEXT;
    }

    @Bean(name = SYSTEM_NAME)
    public ActorSystem getActorSystem() {
        Config config = ConfigFactory.load(AkkaConsts.DEFAULT_CONF_FILE);
        ActorSystem system = ActorSystem.create(SYSTEM_NAME, config);
        AKKA_SYSTEM = system;
        log.info(system.settings().toString());

        return system;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 创建Props
     *
     * @param clazz
     * @param args
     * @return
     */
    public static Props props(Class<? extends Actor> clazz, Object... args) {

        //创建Actor时传递对应的构造参数，当不匹配时会导致IllegalArgumentException异常.
        return Props.create(SpringActorProducer.class, APPLICATION_CONTEXT, clazz, args);
    }

}
