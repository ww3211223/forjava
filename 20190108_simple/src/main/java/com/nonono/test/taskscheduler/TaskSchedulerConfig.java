package com.nonono.test.taskscheduler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.nonono.test.taskscheduler")
@EnableScheduling
public class TaskSchedulerConfig {
}
