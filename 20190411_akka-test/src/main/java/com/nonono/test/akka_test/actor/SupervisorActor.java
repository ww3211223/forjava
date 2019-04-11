package com.nonono.test.akka_test.actor;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import com.nonono.test.akka_test.config.AkkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

/**
 * 服务监视器（顶级）
 */
@Component
@Scope("prototype")
@Slf4j
public class SupervisorActor extends AbstractActor {

    /**
     * 默认名称
     */
    public static String DEFAULT_NAME = "Supervisor";

    /**
     * 容错策略
     */
    public final static SupervisorStrategy STRATEGY =
            new OneForOneStrategy(DeciderBuilder
                    .match(ActorInitializationException.class, e -> stop())
                    .match(ActorKilledException.class, e -> stop())
                    .match(DeathPactException.class, e -> resume())
                    .matchAny(o -> resume()).build()
            );

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder().build();
    }

    @Override
    public void preStart() {
        log.info("SupervisorActor preStart.");
        ActorRef monitor = this.getContext().actorOf(AkkaConfig.props(MonitorActor.class), MonitorActor.DEFAULT_NAME);
        this.getContext().watch(monitor);
    }
}
