package com.zhuxi.product.module.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 为什么 afterCommit：避免在事务未提交前就写缓存，导致读到未提交数据。
 */
@Service
public class UpdateInvalidateExample {

    public void updateProductTransactional(Runnable dbUpdate, Runnable cacheRebuild) {
        dbUpdate.run(); // 事务里执行业务更新
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheRebuild.run(); // 提交后再重建缓存，保证数据可见性
            }
        });
    }
}