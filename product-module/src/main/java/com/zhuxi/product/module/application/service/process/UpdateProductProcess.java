package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "UpdateProductProcess")
public class UpdateProductProcess {

    private final ProductRepository repository;
    private final ProductCacheService cache;
    private final CacheKeyProperties properties;
    private final CommonCacheService commonCache;

    /**
     * 获取商品id
     * @param productSn 商品sn
     * @return 商品id
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Long getProductId(String productSn){
        // 获取商品id
        Long productId = cache.getProductId(productSn);
        if (productId == null){
            try {
                productId = repository.getProductIdBySn(productSn);
                commonCache.saveHashOneValue(properties.getShProductKey() + productSn, "id", productId);
            }catch (BusinessException e){
                commonCache.saveNullValue(properties.getShProductKey() + productSn);
                log.error("获取商品id失败：error：查库获取数据为null,exception:{}.productSn:{}",e.getMessage(),productSn);
                throw e;
            }
        }
        return productId;
    }

    /**
     * 获取卖家id
     * @param userSn 用户sn
     * @return 卖家id
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Long getSellerId(String userSn){
        Long sellerId = commonCache.getUserIdBySn(userSn);
        if (sellerId == null){
            try {
                sellerId = repository.getUserIdBySn(userSn);
            }catch (BusinessException e){
                log.error("获取用户id出错.exception:{}",e.getMessage());
                throw e;
            }
            commonCache.saveUserIdOrSn(userSn,sellerId,1);
        }
        return sellerId;
    }

    /**
     * 更新商品
     * @param update    更新参数
     * @param productId 商品id
     * @param sellerId 卖家id
     * @return 更新后的商品
     */
    public Product updateProduct(UpdateProductDTO update, Long productId,Long sellerId) {
        update.setSellerId(sellerId);
        Product product = new Product();
        product.modify(update,productId);
        return product;
    }


    /**
     * 保存商品
     * @param product 商品
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void save(Product product) {
        repository.save(product);
    }

    @Async
    public void asyncCache(String productSn){
        long threadId = Thread.currentThread().getId();
        Boolean lock = commonCache.getLock(properties.getShProductLockKey() + productSn, String.valueOf(threadId), 30, TimeUnit.SECONDS);
        if (!lock){
            log.warn("getLock-failed,已经有线程在处理缓存,productSn:{}",productSn);
            return;
        }
        try {
            ProductDetailVO detail = repository.getShProductDetail(productSn, true);
            boolean isHotData = checkHotData(detail.getHostScore());
            // cache具体实现

        }finally {
            try {
               commonCache.unLock(properties.getShProductLockKey() + productSn, String.valueOf(threadId));
            }catch (Exception e){
                log.warn("释放锁异常-message:{}", e.getMessage());
            }
        }
    }

    private boolean checkHotData(BigDecimal hostScore){
        if (hostScore == null){
            return false;
        }
        return hostScore.compareTo(BigDecimal.valueOf(80)) >= 0;
    }

}
