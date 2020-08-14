package com.nonono.test.taskscheduler;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleConfigurerTest implements SchedulingConfigurer {

    private String cron = "0/2 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        scheduledTaskRegistrar.addFixedDelayTask(() -> System.out.println("执行定时任务1: " + LocalDateTime.now()), 5000);
        TriggerTask triggerTask = new TriggerTask(() -> {
            System.out.println("执行定时任务2: " + LocalDateTime.now());
        }, triggerContext -> {
            return new CronTrigger("0/2 * * * * ?").nextExecutionTime(triggerContext);
        });

        scheduledTaskRegistrar.addTriggerTask(triggerTask);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    cron = "0/10 * * * * ?";
                    System.out.println("cron 已被修改.");
                } catch (Exception ex) {
                }
            }
        }).start();

        TriggerTask triggerTask2 = new TriggerTask(() -> {
            System.out.println("执行定时任务3: " + LocalDateTime.now() + ", cron:" + cron);
        }, triggerContext -> {
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });

        scheduledTaskRegistrar.addTriggerTask(triggerTask2);
    }
}
