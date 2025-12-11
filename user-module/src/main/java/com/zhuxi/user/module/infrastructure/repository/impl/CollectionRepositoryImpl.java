package com.zhuxi.user.module.infrastructure.repository.impl;

import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.user.module.domain.collection.repository.CollectionRepository;
import com.zhuxi.user.module.infrastructure.mapper.CollectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
/**
 * 收藏领域仓储实现
 * 通过 `CollectionMapper` 执行收藏关系的数据库操作
 */
public class CollectionRepositoryImpl implements CollectionRepository {

    private final CollectionMapper mapper;

    /**
     * 保存收藏关系
     */
    @Override
    public void save(Long userId, Long productId) {
        try {
            mapper.insert(userId, productId);
        }catch (Exception e){
            log.error("collection-insert-error: {}", e.getMessage());
            throw new BusinessException(CommonMessage.DATABASE_INSERT_EXCEPTION);
        }
    }

    /**
     * 删除收藏关系
     */
    @Override
    public void delete(Long userId, Long productId) {
        try {
            mapper.delete(userId, productId);
        }catch (Exception e){
            log.error("collection-delete-error: {}", e.getMessage());
            throw new BusinessException(CommonMessage.DATABASE_DELETE_EXCEPTION);
        }
    }

    /**
     * 分页获取收藏列表
     */
    @Override
    public List<Map<String, Object>> getList(Long userId, int limit, int offset) {
        return mapper.selectList(userId, limit, offset);
    }

    /**
     * 根据用户编号获取用户ID
     */
    @Override
    public Long getUserIdBySn(String userSn) { return mapper.selectUserIdBySn(userSn); }

    /**
     * 根据商品编号获取商品ID
     */
    @Override
    public Long getProductIdBySn(String productSn) { return mapper.selectProductIdBySn(productSn); }
}
