package com.nonono.test.freeMarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private FreeMarkerTest freeMarkerTest;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("freeMarker test is start.");
        freeMarkerTest.test();
        System.out.println("freeMarker test is end.");
    }
}
