package com.nonono.test.metrics_test.controller;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/")
@Slf4j
public class MetricsTestController {

    @Autowired
    private Meter requestMeter;

    @Autowired
    private Histogram responseSizes;

    @Autowired
    private Counter pedingJobs;

    @Autowired
    private Timer responses;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        requestMeter.mark();
        pedingJobs.inc();
        responseSizes.update(new Random().nextInt(100));
        final Timer.Context context = responses.time();

        try {
            Thread.sleep(new Random().nextInt(500));
            return "血祭血神";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "颅献颅座";
        } finally {
            context.stop();
        }
    }
}
