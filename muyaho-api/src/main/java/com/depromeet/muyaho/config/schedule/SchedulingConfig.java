package com.depromeet.muyaho.config.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@EnableScheduling
@Configuration
public class SchedulingConfig implements SchedulingConfigurer {

    private static final int POOL_SIZE = 1;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("myaho-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

}
