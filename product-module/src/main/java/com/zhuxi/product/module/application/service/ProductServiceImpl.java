package com.zhuxi.product.module.application.service;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.LogicalCache;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.application.service.process.*;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.service.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductCacheService cache;
    private final CommonCacheService commonCache;
    private final PublishShProcess publishShProcess;
    private final CacheKeyProperties properties;
    private final GetProductDetailProcess getProductDetailProcess;
    private final UpdateProductProcess updateProductProcess;
    private final GetCategoryProcess getCategoryProcess;
    private final GetShConditionsProcess getShConditionsProcess;
    private final DelProductProcess delProductProcess;



    @Override
    public List<CategoryTreeVO> getCategoryList(int limit, int offset) {

        // 从缓存中直接获取数据
        List<CategoryTreeVO> dataByCache = getCategoryProcess.getDataByCache();
        if (dataByCache != null){
            return dataByCache;
        }

        // 互斥锁 未获取到锁的 睡眠50ms 后尝试重新获取缓存 否则返回List.of()
        long threadId = Thread.currentThread().getId();
        List<CategoryTreeVO> data = getCategoryProcess.getLock(threadId);
        if (data != null){
            return data;
        }

        try {
            // 持锁线程查库并缓存
            return getCategoryProcess.getDataByDb(threadId, limit, offset);
        }finally {
            try {
                commonCache.unLock(properties.getCategoryLockKey(), String.valueOf(threadId));
            }catch (Exception e){
                log.error("释放锁失败：error-message:{}",e.getMessage());
            }
        }
    }

    @Override
    public String publishSh(PublishSHDTO sh, String userSn) {
        // 获取业务所需冗余字段
        List<String> otherNames = publishShProcess.getOtherNames(sh.getConditionId(), sh.getCategoryId());
        // 发布商品
        Product product = publishShProcess.publish(sh, otherNames);

        try{
            //写入
            publishShProcess.save(product);
        }catch (BusinessException e){
            // 失败，保存为草稿
            try {
                publishShProcess.saveDraft(product);
            }catch (BusinessException e1){
                log.error("draft-save-error: {}",e1.getMessage());
                throw e;
            }
            publishShProcess.asyncCache(product);
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR_SAVE_DRAFT);
        }
       // 异步缓存
        publishShProcess.asyncCache( product);
        return product.getProductSn().getSn();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ConditionSHVO> getShConditions() {
        List<ConditionSHVO> shConditions = cache.getShConditions();
        if(shConditions != null){
            return shConditions;
        }

        long threadId = Thread.currentThread().getId();
        List<ConditionSHVO> data = getShConditionsProcess.getLock(shConditions, threadId);
        if (data != null){
            return data;
        }

        try {
            return getShConditionsProcess.getDataByDB(shConditions, threadId);
        }finally {
            try {
                commonCache.unLock(properties.getConditionLockKey(), String.valueOf(threadId));
            }catch (Exception e){
                log.error("释放锁失败：error-message:{}",e.getMessage());
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ShProductVO> getShProductList(ShProductParam shProductParam) {

        boolean isFirst = shProductParam.getLastSn() == null;

        List<ShProductVO> list;
        if ("DESC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }else if ("ASC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,false);
            list = repository.getShProductListAsc(shProductParam);
        }else {
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }
        return list;
    }

    @Override
    public ProductDetailVO getShProductDetail(String productSn) {
        LogicalCache<List<ProductStatic>> productStatics = cache.getProductStatics(productSn);
        Product product = cache.getDetailProductInfo(productSn);
        if (product == null){
            // 商品详细主体信息未命中 直接查库
            return getProductDetailProcess.handleProductNotInCache(productSn, productStatics.getData());
        }
        // 商品详细主体信息命中 处理缓存数据
        return getProductDetailProcess.handlerProductInCache(product, productStatics.getData());
    }

    @Override  //待完善缓存机制
    public void updateProduct(UpdateProductDTO update, String userSn) {
        String productSn = update.getProductSn();
        // 获取商品id
        Long productId = updateProductProcess.getProductId(productSn);

        // 获取用户id
        Long sellerId = updateProductProcess.getSellerId(userSn);

        //修改商品
        Product product = updateProductProcess.updateProduct(update, productId, sellerId);

        //写入
        updateProductProcess.save(product);

        // 删除缓存
        commonCache.delKey(properties.getShProductKey() + productSn);

        // 异步缓存
       updateProductProcess.asyncCache(productSn);
    }

    @Override
    public void delProduct(String productSn) {
        long threadId = Thread.currentThread().getId();
        String lockKey= properties.getDelProductLockKey() + productSn;
        Boolean lock = commonCache.getLock(lockKey, String.valueOf(threadId), 30, TimeUnit.SECONDS);
        if (!lock){
            throw new BusinessException(BusinessMessage.DELING_PRODUCT);
        }
        try {
            // 获取商品id
            Long productId = delProductProcess.getProduct(productSn);
            // 删除商品
            delProductProcess.delProduct(productId, productSn);
            // 缓存失效
            delProductProcess.delCache(productSn);
        }finally {
            commonCache.unLock(lockKey, String.valueOf(threadId));
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MeShProductVO> getMeShProductList(String userSn) {
        return repository.getMeShProductList(repository.getUserIdBySn(userSn));
    }

    private void setProductIdAndTime(ShProductParam shProductParam,boolean isFirst,boolean isDesc){
        if (!isFirst){
            Long productId = repository.getProductIdBySn(shProductParam.getLastSn());
            shProductParam.setProductId(productId);
        }else{
            if (isDesc){
                shProductParam.setProductId(Long.MAX_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.now());
            }else{
                shProductParam.setProductId(Long.MIN_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.MIN);
            }
        }
    }

}
