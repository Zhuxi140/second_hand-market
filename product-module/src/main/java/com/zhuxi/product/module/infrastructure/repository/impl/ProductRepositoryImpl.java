package com.zhuxi.product.module.infrastructure.repository.impl;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.PersistenceException;
import com.zhuxi.product.module.domain.model.Product;
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
            throw new BusinessException(BusinessMessage.GET_SH_CONDITION_ERROR);
        }
        return productId;
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
    public ProductDetailVO getProductDetail(String productId) {
        ProductDetailVO productDetail = productMapper.getProductDetail(productId);
        List<ProductImages> productImages = productMapper.getProductImages(productId);
        productDetail.setProductImages(productImages);
        return productDetail;
    }
}
