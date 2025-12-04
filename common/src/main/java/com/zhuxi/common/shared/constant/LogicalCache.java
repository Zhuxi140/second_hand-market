package com.zhuxi.common.shared.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
public class LogicalCache<T> {
    private T data;
    private long expireTime;

    public LogicalCache(T data, long ttl) {
        this.data = data;
        this.expireTime = TimeUnit.SECONDS.toMillis(ttl) + System.currentTimeMillis();
    }
}
