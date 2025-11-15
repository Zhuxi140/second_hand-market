package com.zhuxi.common.domain.service;

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
}
