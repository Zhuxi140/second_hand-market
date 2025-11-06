package com.zhuxi.user.module.application.service;

import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.CacheMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.CacheException;
import com.zhuxi.common.shared.utils.RedisUtils;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.service.UserCacheService;
import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Slf4j
@Service
public class UserCacheServiceImpl implements UserCacheService {

    private final CacheKeyProperties commonKeys;
    private final RedisUtils redisUtils;
    private final DefaultProperties properties;
    private final DefaultRedisScript<Long> hashSetWithExpireScript;
    private final DefaultRedisScript<List<Object>> getUserInfoScript;

    public UserCacheServiceImpl(CacheKeyProperties commonKeys, RedisUtils redisUtils, DefaultProperties properties) {
        this.commonKeys = commonKeys;
        this.redisUtils = redisUtils;
        this.properties = properties;
        this.getUserInfoScript = new DefaultRedisScript<>();
        this.hashSetWithExpireScript = new DefaultRedisScript<>();
        ClassPathResource classPathResource1 = new ClassPathResource("lua/getUserInfo.lua");
        ClassPathResource classPathResource = new ClassPathResource("lua/saveUserInfo.lua");
        ResourceScriptSource resourceScriptSource1 = new ResourceScriptSource(classPathResource1);
        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);
        hashSetWithExpireScript.setScriptSource(resourceScriptSource);
        getUserInfoScript.setScriptSource(resourceScriptSource1);
        getUserInfoScript.setResultType((Class<List<Object>>) (Class<?>) List.class);
        hashSetWithExpireScript.setResultType(Long.class);
    }

    @Override
    public void saveBlockList(String token, Role role,long expire) {
        try {
            if (Role.user.equals(role) || Role.Merchant.equals(role)) {
                redisUtils.ssSetValue(commonKeys.getBlockUserTokenKey() + token, "1",
                        expire, TimeUnit.MILLISECONDS);
            } else {
                redisUtils.ssSetValue(commonKeys.getBlockAdminTokenKey() + token, "1",
                        expire, TimeUnit.MILLISECONDS);
            }
        }catch (Exception e){
            log.error("save blockToken to redis---error:{}",e.getMessage());
            //待完善  重试或者告警等逻辑
        }
    }

    @Override
    public void saveUserInfo(String userSn, User user) {
        if (userSn == null || userSn.isBlank() || user == null){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }

        String hashKey = properties.getUserInfoKey() + userSn;
        List<String> keys = new ArrayList<>();
        keys.add(hashKey);
        List<Object> values = new ArrayList<>();

        values.add(properties.getUserInfoExpire());
        values.add("id");
        values.add(user.getId());
        values.add("userSn");
        values.add(user.getUserSn());
        values.add("username");
        values.add(user.getUsername());
        values.add("nickname");
        values.add(user.getNickname());
        values.add("phone");
        values.add(user.getPhone().getNumber());
        values.add("email");
        values.add(user.getEmail() == null ? null : user.getEmail().getAddress());
        values.add("avatar");
        values.add(user.getAvatar());
        values.add("gender");
        values.add(user.getGender());
        values.add("role");
        values.add(user.getRole().getId());
        values.add("userStatus");
        values.add(user.getUserStatus().getCode());
        values.add("createdAt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        values.add(user.getCreatedAt().format(formatter));

        redisUtils.executeLuaScript(hashSetWithExpireScript, keys, values);
    }

    @Override
    public void saveUserPartInfo(String userSn, User user, List<String> keys) {
        if (userSn == null || userSn.isBlank() || keys == null || keys.isEmpty() || user == null){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }

        String hashKey = properties.getUserInfoKey() + userSn;
        List<String> key = new ArrayList<>();
        key.add(hashKey);
        List<Object> values = new ArrayList<>();
        values.add(properties.getUserInfoExpire());

        if (keys.contains("id")){
            values.add("id");
            values.add(user.getId());
        }
        if (keys.contains("userSn")){
            values.add("userSn");
            values.add(user.getUserSn());
        }
        if (keys.contains("username")){
            values.add("username");
            values.add(user.getUsername());
        }
        if (keys.contains("nickname")){
            values.add("nickname");
            values.add(user.getNickname());
        }
        if (keys.contains("phone")){
            values.add("phone");
            values.add(user.getPhone().getNumber());
        }
        if (keys.contains("email")){
            values.add("email");
            values.add(user.getEmail().getAddress());
        }
        if (keys.contains("avatar")){
            values.add("avatar");
            values.add(user.getAvatar());
        }
        if (keys.contains("gender")){
            values.add("gender");
            values.add(user.getGender());
        }
        if (keys.contains("role")){
            values.add("role");
            values.add(user.getRole().getId());
        }
        if (keys.contains("userStatus")){
            values.add("userStatus");
            values.add(user.getUserStatus().getCode());
        }
        if (keys.contains("createdAt")){
            values.add("createdAt");
            values.add(user.getCreatedAt());
        }

        redisUtils.executeLuaScript(hashSetWithExpireScript, key, values);
    }

    @Override
    public void saveUserPartInfo(String userSn, List<Object> values) {
        if (userSn == null || userSn.isBlank() || values == null || values.isEmpty()){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }

        String hashKey = properties.getUserInfoKey() + userSn;
        List<String> key = new ArrayList<>();
        key.add(hashKey);
        List<Object> value = new ArrayList<>();
        value.add(properties.getUserInfoExpire());
        value.addAll(values);

        redisUtils.executeLuaScript(hashSetWithExpireScript, key, value);
    }

    @Override
    public Object getOneInfo(String userSn, String key) {
        return redisUtils.hashGet(properties.getUserInfoKey() + userSn, key);
    }

    @Override
    public void deleteUserInfo(String userSn) {
        if (userSn == null || userSn.isBlank()){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }
        redisUtils.deleteKey(properties.getUserInfoKey() + userSn);
    }

    @Override
    public User getUserInfo(String userSn,List<String> keys) {
        if (userSn == null || userSn.isBlank()){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }

        if (keys == null || keys.isEmpty()){
            keys = List.of("id", "userSn", "username", "nickname", "phone", "email", "avatar", "gender", "role", "userStatus", "createdAt");
        }
        String key  = properties.getUserInfoKey() + userSn;

        Object o = redisUtils.executeLuaScript(
                getUserInfoScript,
                List.of(key),
                keys
        );

        List<Object> objects = null;
        if (o instanceof List<?> list){
            objects = list.stream()
                    .map(e -> (Object) e)
                    .toList();
        }

        if (objects == null || objects.isEmpty() || objects.stream().allMatch(Objects::isNull)){
            log.warn("redis中未找到用户信息,userSn:{}", userSn);
            return null;
        }else {
            User user = new User();
            Map<String, Object> map = toMap(keys, objects);
            return user.buildCacheUserInfo(map);
        }
    }

    private Map<String,Object> toMap(List<String> keys,List<Object> values){
        if (keys.size() != values.size()){
            throw new CacheException(CacheMessage.KEYS_AND_VALUES_SIZE_NOT_EQUAL);
        }
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i),values.get(i));
        }
        return map;
    }
}
