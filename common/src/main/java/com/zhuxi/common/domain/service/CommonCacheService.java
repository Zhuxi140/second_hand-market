package com.zhuxi.common.domain.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
     * 通用String类型（so）的value
     * @param key 缓存key
     * @return 缓存数据
     */
    Object soGetValue(String key);

    /**
     * 获取用户编号
     * @param userId 用户编号
     * @return 用户编号
     */
    String getUserSn(Long userId);

    /**
     * 缓存缺失的用户id或缓存加入id-sn映射
     *
     * @param userSn 用户编号
     * @param userId 用户id
     * @param type   0:用户id-sn映射    1:缓存缺失的用户id      3: 0 and 1
     */
    void saveUserIdOrSn(String userSn,Long userId,Integer type);

    /**
     * 缓存空值 避免缓存穿透
     * @param key 缓存key
     */
    void saveNullValue(String key);

    /**
     * 缓存hash类型中单个字段的值
     * @param hashKey 键名key
     * @param field 字段
     * @param value 值
     */
    void saveHashOneValue(String hashKey, String field, Object value);

    /**
     * 获取锁(互斥锁)
     * @param key 缓存key
     * @param value 缓存value
     * @param timeout 锁过期时间
     * @param unit 时间单位
     * @return true:获取锁成功 false:获取锁失败
     */
    Boolean getLock(String key,String value, long timeout, TimeUnit unit);

    /**
     * 释放锁
     * @param key 缓存key
     * @param threadId 线程id
     * @return true:释放锁成功 false:释放锁失败
     */
    Boolean unLock(String key,String threadId);


    /**
     * 删除缓存
     * @param key 缓存key
     */
    void delKey(String key);

    /**
     * 删除hash类型中单个字段
     * @param hashKey 键名key
     * @param field 字段
     */
    void delHashField(String hashKey, String field);

    /**
     * 更新缺失的缓存(hash类型)
     * @param map 缓存数据 <String(字段),Object(值)>
     * @param hashKey 键名key
     */
    void hashFlushValue(Map<String,Object> map, String hashKey);


    /**
     * 获取hash类型中单个字段的值
     * @param hashKey 键名key
     * @param field 字段
     * @return 值
     */
    Object getHashOneValue(String hashKey, String field);
}
