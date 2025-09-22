package com.zhuxi.server.service.impl;

import com.zhuxi.common.constant.TransactionMessage;
import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.Product.ProductSdDTO;
import com.zhuxi.pojo.entity.Product;
import com.zhuxi.server.helper.ProductMapperHelper;
import com.zhuxi.server.service.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductMapperHelper productMapperHelper;
    
    /**
     * 发布二手商品
     */
    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> pSdProduct(ProductSdDTO product,String userSn,Integer isDraft) {
        if (isDraft == null){
            Result.fail(TransactionMessage.DRAFT_NOT_NULL);
        }
        productMapperHelper.psdProduct(product,isDraft);
        return Result.success("发布成功");
    }
    
    /**
     * 根据ID删除商品
     */
    @Override
    public int deleteById(Long id) {
        return productMapperHelper.deleteById(id);
    }
    
    /**
     * 更新商品
     */
    @Override
    public int update(Product product) {
        return productMapperHelper.update(product);
    }
    
    /**
     * 根据ID查询商品
     */
    @Override
    public Product selectById(Long id) {
        return productMapperHelper.selectById(id);
    }
    
    /**
     * 根据商品编号查询商品
     */
    @Override
    public Product selectByProductSn(String productSn) {
        return productMapperHelper.selectByProductSn(productSn);
    }
    
    /**
     * 根据卖家ID查询商品列表
     */
    @Override
    public List<Product> selectBySellerId(Long sellerId) {
        return productMapperHelper.selectBySellerId(sellerId);
    }
    
    /**
     * 查询所有商品
     */
    @Override
    public List<Product> selectAll() {
        return productMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询商品列表
     */
    @Override
    public List<Product> selectByCondition(Product product) {
        return productMapperHelper.selectByCondition(product);
    }
}
