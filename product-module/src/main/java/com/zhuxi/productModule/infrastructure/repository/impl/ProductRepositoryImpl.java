package com.zhuxi.productModule.infrastructure.repository.impl;

import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.exception.PersistenceException;
import com.zhuxi.productModule.domain.model.Product;
import com.zhuxi.productModule.domain.repository.ProductRepository;
import com.zhuxi.productModule.infrastructure.mapper.ProductMapper;
import com.zhuxi.productModule.interfaces.vo.CategoryVO;
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
    @Override
    public List<CategoryVO> getCategoryList(int limit, int offset) {
        try {
            return productMapper.getCategoryList(limit, offset);
        }catch (Exception e){
            log.error("getCategoryList-case:{} message:{}", CommonMessage.DATABASE_SELECT_EXCEPTION,e.getMessage());
            throw new BusinessException(BusinessMessage.GET_CATEGORY_ERROR);
        }
    }

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
}
