package com.zhuxi.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 为什么自定义线程池：避免使用公共 ForkJoin 池导致不可控；可监控、可限流。
 */
@Configuration
public class AsyncPoolConfig {
    @Bean
    public ThreadPoolTaskExecutor cacheExecutor() {
        ThreadPoolTaskExecutor e = new ThreadPoolTaskExecutor();
        e.setCorePoolSize(8);
        e.setMaxPoolSize(32);
        e.setQueueCapacity(1000);
        e.setThreadNamePrefix("cache-");
        e.initialize();
        return e;
    }
}