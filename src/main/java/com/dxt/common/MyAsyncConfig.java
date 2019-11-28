package com.dxt.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class MyAsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(10);
        // 线程池最大线程数
        taskExecutor.setMaxPoolSize(30);
        // 线程队列最大线程数
        taskExecutor.setQueueCapacity(2000);
        // 初始化
        taskExecutor.initialize();
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        taskExecutor.setAwaitTerminationSeconds(0);
        return taskExecutor;
    }

}
