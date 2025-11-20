package com.zhuxi.product.module.infrastructure.repository.impl;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.PersistenceException;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.infrastructure.mapper.ProductMapper;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuxi
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper productMapper;

    // 商品分类列表
    @Override
    public List<CategoryVO> getCategoryList(int limit, int offset) {
        try {
            return productMapper.getCategoryList(limit, offset);
        }catch (Exception e){
            log.error("getCategoryList-case:{} message:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,e.getMessage());
            throw new BusinessException(BusinessMessage.GET_CATEGORY_ERROR);
        }
    }

    // 保存或修改商品
    @Override
    public void save(Product product) {
            if (product.getId() != null) {
                int update = productMapper.update(product);
                if (update <= 0){
                    log.error("product-update-case:{}", CommonMessage.DATABASE_UPDATE_EXCEPTION);
                    throw new BusinessException(BusinessMessage.UPDATE_PRODUCT_ERROR);
                }
            } else {
                int insert = productMapper.insert(product);
                if (insert <= 0){
                    log.error("product-save-case:{}", CommonMessage.DATABASE_INSERT_EXCEPTION);
                    throw new PersistenceException(BusinessMessage.SAVE_PRODUCT_ERROR);
                }
            }
    }

    @Override
    public Long getProductIdBySn(String productSn) {
        Long productId = productMapper.getProductIdBySn(productSn);
        if (productId == null){
            log.error("getProductIdBySn-case:{},productSn:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,productSn);
            throw new BusinessException(BusinessMessage.PRODUCT_ID_IS_NULL);
        }
        return productId;
    }

    @Override
    public Long getUserIdBySn(String userSn) {
        Long userId = productMapper.getUserIdBySn(userSn);
        if (userId == null){
            log.error("getUserIdBySn-case:{},userSn:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,userSn);
            throw new BusinessException(BusinessMessage.USER_ID_IS_NULL);
        }
        return userId;
    }

    // 获取二手商品成色列表
    @Override
    public List<ConditionSHVO> getShConditions() {
        try {
            return productMapper.getShConditions();
        }catch (Exception e){
            log.error("getShConditions-case:{} message:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,e.getMessage());
            throw new BusinessException(BusinessMessage.GET_SH_CONDITION_ERROR);
        }
    }

    @Override
    public List<ShProductVO> getShProductListDesc(ShProductParam param) {
        return productMapper.getShProductListDesc(param);
    }

    @Override
    public List<ShProductVO> getShProductListAsc(ShProductParam shProductParam) {
        return productMapper.getShProductListAsc(shProductParam);
    }

    @Override
    public ProductDetailVO getShProductDetail(String productSn,boolean isGetStatic) {
        Long productId = productMapper.getProductIdBySn(productSn);
        if (productId == null){
            log.error("getShProductDetail:getProductIdBySn-case:{},productSn:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,productSn);
            throw new BusinessException(BusinessMessage.PRODUCT_ID_IS_NULL);
        }
        ProductDetailVO productDetail = productMapper.getShProductDetail(productId);
        if (productDetail == null){
            log.error("getShProductDetail:getShProductDetail-case:{},productSn:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,productSn);
            return null;
        }
        if (isGetStatic){
            List<ProductImage> productImages = productMapper.getProductImages(productId);
            if (productImages == null){
                log.error("getShProductDetail:getProductImages-case:{},productSn:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,productSn);
            }
            productDetail.setProductImages(productImages);
        }
        productDetail.setId(productId);
        return productDetail;
    }

    @Override
    public void delProduct(Long productId) {
        Integer result = productMapper.delProduct(productId);
        if (result != 1){
            log.error("delProduct-case:{},productId:{}", CommonMessage.DATABASE_DELETE_EXCEPTION,productId);
            throw new BusinessException(BusinessMessage.DEL_PRODUCT_ERROR);
        }
    }

    @Override
    public List<MeShProductVO> getMeShProductList(Long userId) {
        try {
            return productMapper.getMeShProductList(userId);
        }catch (Exception e){
            log.error("getMeShProductList-error:case:{} ",e.getMessage());
            throw new BusinessException(BusinessMessage.GET_SH_PRODUCT_LIST_ERROR);
        }
    }

    @Override
    public String gerConditionNameById(Integer conditionId) {
        return productMapper.gerConditionNameById(conditionId);
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        return productMapper.getCategoryNameById(categoryId);
    }

    @Override
    public List<ProductStatic> getProductStatics(Long productId) {
        List<ProductStatic> productStatics = productMapper.getProductStatics(productId);
        if (productStatics == null){
            log.error("getProductStatics-case:{}.productId:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,productId);
        }
        return productStatics;
    }

    @Override
    public List<Object> getSellerInfo(String userSn) {
        return productMapper.getSellerInfo(userSn);
    }

    @Override
    public Product getProductForCache(Long productId) {
        return productMapper.getProductForCache(productId);
    }

    @Override
    public String getUserSn(Long userId) {
        return productMapper.getUserSn(userId);
    }
}
