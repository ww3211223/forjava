package com.nonono.test.akka_test.main;

import com.nonono.test.akka_test.actor.SupervisorActor;
import com.nonono.test.akka_test.config.AkkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobInitializer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AkkaConfig.getAkkaSystem().actorOf(AkkaConfig.props(SupervisorActor.class), SupervisorActor.DEFAULT_NAME);
    }
}
