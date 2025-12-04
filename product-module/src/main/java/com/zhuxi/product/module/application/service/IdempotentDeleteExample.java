package com.zhuxi.product.module.application.service;

import org.springframework.stereotype.Service;

/**
 * 为什么先查存在性：减少不必要操作；为何统一失效：防止派生缓存残留。
 */
@Service
public class IdempotentDeleteExample {
    public void delete(Long id, Runnable invalidate) {
        boolean existed = exists(id);
        if (!existed) {
            invalidate.run(); // 幂等：即使不存在也尝试失效相关缓存
            return;
        }
        logicDelete(id);
        invalidate.run();
    }
    private boolean exists(Long id) { return true; }
    private void logicDelete(Long id) { /* 执行逻辑删除 */ }
}