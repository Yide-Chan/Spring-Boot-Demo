package com.chenyide.scheduledtasks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author chenyide
 * @version v1.0
 * @className ScheduledConfig
 * @description 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * <p>
 * /@EnableAsync：开启异步事件的支持
 * @date 2024/6/11 20:31
 **/

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.chenyide.scheduledtasks.job"})
public class ScheduledConfig implements SchedulingConfigurer {

    @Value("spring.task.scheduling.pool.core-size")
    private int corePoolSize;
    @Value("spring.task.scheduling.pool.max-size")
    private int maxPoolSize;
    @Value("spring.task.scheduling.pool.queue-capacity")
    private int queueCapacity;
    @Value("spring.task.scheduling.pool.thread-name-prefix")
    private String threadNamePrefix;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor1());
//        taskRegistrar.setScheduler(taskExecutor2());
    }

    // 方法一
    @Bean
    public Executor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    // 方法二
    @Bean
    public Executor taskExecutor2() {
        CustomizableThreadFactory factory = new CustomizableThreadFactory();
        factory.setThreadNamePrefix(threadNamePrefix);
        return new ScheduledThreadPoolExecutor(maxPoolSize, factory);
    }
}
