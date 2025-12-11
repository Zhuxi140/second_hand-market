package com.zhuxi.user.module.domain.collection.service;

import java.util.List;
import java.util.Map;

/**
 * 收藏领域服务接口
 * 定义收藏相关的用例操作
 */
public interface CollectionService {
    /**
     * 收藏商品
     */
    void add(String userSn, String productSn);

    /**
     * 取消收藏
     */
    void del(String userSn, String productSn);

    /**
     * 获取收藏列表
     */
    List<Map<String,Object>> list(String userSn, int page, int size);
}
