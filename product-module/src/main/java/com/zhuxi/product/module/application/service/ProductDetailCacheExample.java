package com.zhuxi.product.module.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 为什么广播：删除/下架要通知其他副本/实例的缓存失效，避免跨实例脏读。
 */
@Service
public class ProductDetailCacheExample {

    private final StringRedisTemplate redis;
    private final ApplicationEventPublisher publisher;
    private static final String NULL = "NULL_VALUE";

    public ProductDetailCacheExample(StringRedisTemplate redis, ApplicationEventPublisher publisher) {
        this.redis = redis;
        this.publisher = publisher;
    }

    public String getDetail(String sn) {
        String key = "product:detail:" + sn;
        String val = redis.opsForValue().get(key);
        if (NULL.equals(val)) return null;
        if (val != null) return val;
        String db = loadFromDb(sn);
        if (db == null) {
            redis.opsForValue().set(key, NULL); // 为什么负缓存：防穿透
            redis.expire(key, java.time.Duration.ofMinutes(5));
            return null;
        }
        redis.opsForValue().set(key, db, java.time.Duration.ofMinutes(30)); // 为什么 TTL：防止永久脏数据
        return db;
    }

    public void invalidateAllOnDelete(String sn) {
        // 为什么集中失效：保证详情、静态、列表等派生缓存同时失效
        redis.delete("product:detail:" + sn);
        redis.delete("product:statics:" + sn);
        publisher.publishEvent(new ProductDeletedEvent(sn));
    }

    private String loadFromDb(String sn) { /* 模拟 */ return "{\"sn\":\"" + sn + "\"}"; }

    public record ProductDeletedEvent(String sn) { }
}