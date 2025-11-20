package com.zhuxi.common.domain.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxi
 */
public interface CommonCacheService {

    /**
     * 根据用户编号获取用户id
     *
     * @param userSn 用户编号
     * @return 用户id
     */
    Long getUserIdBySn(String userSn);

    /**
     * 缓存缺失的用户id或缓存加入id-sn映射
     *
     * @param userSn 用户编号
     * @param userId 用户id
     * @param type   0:用户id-sn映射 1:缓存缺失的用户id 3: 0 and 1
     */
    void saveUserIdOrSn(String userSn,Long userId,Integer type);

    /**
     * 缓存空值 避免缓存穿透
     * @param key 缓存key
     */
    void saveNullValue(String key);


    /**
     * 删除缓存
     * @param key 缓存key
     */
    void delKey(String key);

    /**
     * 更新缺失的缓存(hash类型)
     * @param map 缓存数据 <String(字段),Object(值)>
     * @param hashKey 键名key
     */
    void hashFlushValue(Map<String,Object> map, String hashKey);
}
