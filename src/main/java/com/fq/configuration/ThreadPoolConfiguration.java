/*
Date: 04/15,2019, 18:17
*/
package com.fq.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@PropertySource({"classpath:my.properties"})
public class ThreadPoolConfiguration {

    @Value("${threadpool.poolsize}")
    private int poolsize;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(poolsize);
        // 设置最大线程数
        executor.setMaxPoolSize(poolsize);

        // 设置队列容量
        // executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
//        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
//        executor.setThreadNamePrefix("hello-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        return executor;
    }
}
