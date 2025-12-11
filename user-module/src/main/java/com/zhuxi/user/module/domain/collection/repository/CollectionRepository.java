package com.zhuxi.user.module.domain.collection.repository;

import java.util.List;
import java.util.Map;

/**
 * 收藏领域仓储接口
 * 抽象收藏关系的数据访问操作
 */
public interface CollectionRepository {
    /**
     * 保存收藏关系
     */
    void save(Long userId, Long productId);

    /**
     * 删除收藏关系
     */
    void delete(Long userId, Long productId);

    /**
     * 分页获取收藏列表
     */
    List<Map<String,Object>> getList(Long userId, int limit, int offset);

    /**
     * 根据用户编号获取用户ID
     */
    Long getUserIdBySn(String userSn);

    /**
     * 根据商品编号获取商品ID
     */
    Long getProductIdBySn(String productSn);
}
