package com.nonono.test.akka_test.actor;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import com.nonono.test.akka_test.config.AkkaConfig;
import com.nonono.test.akka_test.message.ActorMessage;
import com.nonono.test.akka_test.message.TickMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
@Slf4j
public class MonitorActor extends AbstractActorWithTimers {

    public static String DEFAULT_NAME = "monitor";

    private int tickQuantity = 0;

    private ActorRef workerRef;

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(TickMessage.class, m -> {
                    tickQuantity++;
                    log.info("monitor receive tick. tickQuantity:{}", tickQuantity);

                    ActorMessage message = new TickMessage();
                    message.message = "tickQuantity:" + tickQuantity;
                    workerRef.tell(message, this.self());
                })
                .build();
    }

    @Override
    public void preStart() throws Exception {
        log.info("MonitorActor preStart.");

        ActorRef worker = this.getContext().actorOf(AkkaConfig.props(WorkActor.class), WorkActor.DEFAULT_NAME);
        this.workerRef = worker;
        this.getContext().watch(worker);

        this.getTimers().startPeriodicTimer(TickMessage.DEFAULT_KEY, TickMessage.builder().build(), Duration.create(1 * 1000, TimeUnit.MILLISECONDS));
    }
}
