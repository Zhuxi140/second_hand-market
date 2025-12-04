package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.infrastructure.repository.impl.ProductRepositoryImpl;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "GetShConditionsProcess")
public class GetShConditionsProcess {
    private final ProductRepositoryImpl repository;
    private final ProductCacheService cache;
    private final CommonCacheService commonCache;
    private final CacheKeyProperties properties;

    public List<ConditionSHVO> getLock(List<ConditionSHVO> shConditions, Long threadId){
        boolean lock = Boolean.TRUE.equals(commonCache.getLock(properties.getConditionKey(), threadId, 30, TimeUnit.SECONDS));
        if (!lock){
            try{
                sleep(50);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            shConditions = cache.getShConditions();
            return shConditions == null ? List.of() : shConditions;
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ConditionSHVO> getDataByDB(List<ConditionSHVO> shConditions, Long threadId) {
        try {
            shConditions = repository.getShConditions();
            List<ConditionSHVO> finalShConditions = shConditions;
            CompletableFuture.runAsync(() -> cache.saveConditionList(finalShConditions));
        } catch (BusinessException e) {
            commonCache.saveNullValue(properties.getConditionKey());
            log.error("获取商品成色列表失败：error：查库获取数据为null,exception_message:{}.condition-key:{}", e.getMessage(), properties.getConditionKey());
            return List.of();
        } finally {
            try {
                Object currentLock = commonCache.soGetValue(properties.getConditionLockKey());
                if (currentLock != null && currentLock.equals(threadId)) {
                    commonCache.delKey(properties.getConditionLockKey());
                }
            } catch (Exception e) {
                log.warn("释放锁异常-message:{}", e.getMessage());
            }
        }
        return shConditions;
    }
}
