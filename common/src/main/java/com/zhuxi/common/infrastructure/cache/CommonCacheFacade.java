package com.zhuxi.common.infrastructure.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 为什么要统一：减少各模块重复实现，避免策略不一致引发脏读/穿透。
 */
@Component
public class CommonCacheFacade {

    private final StringRedisTemplate redis;
    private final ThreadPoolTaskExecutor executor;
    private static final String NULL = "NULL_VALUE";

    public CommonCacheFacade(StringRedisTemplate redis, ThreadPoolTaskExecutor executor) {
        this.redis = redis;
        this.executor = executor;
    }

    public String getOrLoad(String key, long ttlSeconds, long negativeTtl, Supplier<String> loader) {
        String val = redis.opsForValue().get(key);
        if (NULL.equals(val)) return null;
        if (val != null) {
            executor.execute(() -> refreshAsync(key, ttlSeconds, loader)); // 为什么异步刷新：降低高并发读延迟
            return val;
        }
        String lockKey = "lock:" + key;
        boolean locked = Boolean.TRUE.equals(redis.opsForValue().setIfAbsent(lockKey, "1", 30, TimeUnit.SECONDS));
        if (locked) {
            try {
                String fresh = loader.get();
                if (fresh == null || fresh.isBlank()) {
                    redis.opsForValue().set(key, NULL, negativeTtl, TimeUnit.SECONDS);
                    return null;
                }
                redis.opsForValue().set(key, fresh, ttlSeconds, TimeUnit.SECONDS);
                return fresh;
            } finally {
                redis.delete(lockKey);
            }
        } else {
            // 单航班：等待持锁者写入，避免多路读打穿库
            sleep(50);
            return redis.opsForValue().get(key);
        }
    }

    private void refreshAsync(String key, long ttlSeconds, Supplier<String> loader) {
        String val = redis.opsForValue().get(key);
        if (val == null || NULL.equals(val)) return;
        String fresh = loader.get();
        if (fresh != null && !fresh.isBlank()) {
            redis.opsForValue().set(key, fresh, ttlSeconds, TimeUnit.SECONDS);
        }
    }

    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}