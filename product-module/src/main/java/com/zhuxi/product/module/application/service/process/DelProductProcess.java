package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.domain.service.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "DelProductProcess")
public class DelProductProcess {

    private final ProductRepository repository;
    private final ProductCacheService cache;
    private final CommonCacheService commonCache;
    private final CacheKeyProperties properties;

    public Long getProduct(String productSn){
        Long productId = cache.getProductId(productSn);
        if (productId == null){
            try {
                productId = repository.getProductIdBySn(productSn);
            }catch (BusinessException e){
                commonCache.saveNullValue(properties.getShProductKey() + productSn);
                throw new BusinessException(BusinessMessage.ALREADY_DEL_PRODUCT);
            }
        }
        return productId;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delProduct(Long productId, String productSn){
        repository.delProduct(productId);
    }

    @Async
    public void delCache(String productSn){
        commonCache.delKey(properties.getShProductKey() + productSn);
        commonCache.delKey(properties.getProductStaticKey() + productSn);

    }
}
