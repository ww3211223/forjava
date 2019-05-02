package com.nonono.test.akka_test.actor;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.routing.ConsistentHashingPool;
import akka.routing.ConsistentHashingRouter;
import akka.routing.RoundRobinGroup;
import com.nonono.test.akka_test.config.AkkaConfig;
import com.nonono.test.akka_test.message.ActorMessage;
import com.nonono.test.akka_test.message.TickMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
@Slf4j
public class MonitorActor extends AbstractActorWithTimers {

    public static String DEFAULT_NAME = "Monitor";

    private int tickQuantity = 0;

    private int workerActorQuantity = 3;

    private ActorRef workerRef;

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(TickMessage.class, m -> {
                    if (tickQuantity >= 10) {
                        return;
                    }

                    tickQuantity++;
                    log.info("monitor receive tick. tickQuantity:{}", tickQuantity);

                    ActorMessage message = new ActorMessage();
                    message.setId(tickQuantity);
                    message.setMessage("tickQuantity:" + tickQuantity);
                    workerRef.tell(message, this.self());
                })
                .match(Terminated.class, t -> {
                    log.info("monitor receive terminated. path:{}", t.actor().path());
                })
                .build();
    }

    @Override
    public void preStart() throws Exception {
        log.info("MonitorActor preStart.");

        //以编码方式指定路由规则
//        ActorRef worker = this.getContext().actorOf(new RoundRobinPool(3)
//                .props(AkkaConfig.props(WorkActor.class)), WorkActor.DEFAULT_NAME);

        //以配置方式指定路由规则
//        ActorRef worker = this.getContext().actorOf(FromConfig.getInstance().props(AkkaConfig.props(WorkActor.class)), WorkActor.DEFAULT_NAME);

        //以Group的方式指定路由规则 不能指定props，导致无法使用spring注入相应的Bean
//        List<String> paths = Arrays.asList("/user/Supervisor/Monitor/Worker/Worker1", "/user/Supervisor/Monitor/Worker/Worker2", "/user/Supervisor/Monitor/Worker/Worker3");
//        ActorRef worker = this.getContext().actorOf(new RoundRobinGroup(paths).props(), WorkActor.DEFAULT_NAME);

        //以一致性哈希方式路由
        ActorRef worker = this.getContext().actorOf(new ConsistentHashingPool(workerActorQuantity).withHashMapper(hashMapper)
                .props(AkkaConfig.props(WorkActor.class)), WorkActor.DEFAULT_NAME);

        this.workerRef = worker;
        this.getContext().watch(worker);

        this.getTimers().startPeriodicTimer(TickMessage.DEFAULT_KEY, new TickMessage(), Duration.create(1 * 1000, TimeUnit.MILLISECONDS));
    }

    /**
     * 一致性哈希
     */
    private final ConsistentHashingRouter.ConsistentHashMapper hashMapper = new ConsistentHashingRouter.ConsistentHashMapper() {
        @Override
        public Object hashKey(Object message) {
            if (message instanceof ActorMessage) {
                return ((ActorMessage) message).getId() % workerActorQuantity;
            }

            return null;
        }
    };
}
