package com.nonono.test.akka_test.actor;

import akka.actor.AbstractActor;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import com.nonono.test.akka_test.message.ActorMessage;
import com.nonono.test.akka_test.services.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class WorkActor extends AbstractActor {

    @Autowired
    private WorkService workService;

    /**
     * 默认名称
     */
    public static String DEFAULT_NAME = "Worker";

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return SupervisorActor.STRATEGY;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(Terminated.class, m -> log.error("{} stopped.", m.actor().path()))
                .match(ActorMessage.class, m ->
                {
                    log.info("workActor receive message:{}, actorPath:{}", m.getMessage(), this.self().path());
                    workService.work();
                })
                .build();
    }

    @Override
    public void preStart() {
        log.info("WorkActor preStart.");
    }
}
