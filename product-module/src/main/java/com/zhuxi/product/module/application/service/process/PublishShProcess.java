package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.domain.enums.IsDraft;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.service.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "PublishShProcess")
public class PublishShProcess {

    private final ProductRepository repository;
    private final ProductCacheService cache;

    /**
     * 获取业务需要的冗余字段
     * @param conditionId 成色id
     * @param categoryId 分类id
     * @return 业务需要的冗余字段
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> getOtherNames(Integer conditionId, Long categoryId) {
        String categoryName = repository.getCategoryNameById(categoryId);
        String conditionName = repository.gerConditionNameById(conditionId);

        return List.of(categoryName, conditionName);
    }

    /**
     * 发布商品
     * @param sh 商品信息DTO
     * @param otherNames 业务需要的冗余字段
     * @return 商品信息
     */
    public Product publish(PublishSHDTO sh,List<String> otherNames){
        Product product = new Product();
        product.publish(sh,1L,otherNames);
        return product;
    }

    /**
     * 保存商品
     * @param product 商品领域对象
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void save(Product product){
        repository.save(product);
    }

    /**
     * 保存商品草稿
     * @param product 草稿商品
     */
    @Transactional(rollbackFor = BusinessException.class,propagation = Propagation.REQUIRES_NEW)
    public void saveDraft(Product product){
        product.modifyToDraft(IsDraft.ENABLE);
        try {
            repository.save(product);
        }catch (BusinessException e){
            log.error("draftSave-error: {}",e.getMessage());
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR);
        }
    }

    @Async
    public void asyncCache(Product product){
        // 异步缓存
        CompletableFuture.runAsync(()->{
            // 缓存商品信息
            cache.saveShProduct(product,product.getProductSn().getSn(),false);
            // 缓存商品静态信息
            List<ProductStatic> productStatics = repository.getProductStatics(product.getId());
            if (productStatics != null){
                cache.saveProductStatic(productStatics,product.getProductSn().getSn(),false);
            }else{
                log.warn("publishShProduct-error:查库获取商品静态信息为null. productId:{}",product.getId());
            }
        });
    }
}
