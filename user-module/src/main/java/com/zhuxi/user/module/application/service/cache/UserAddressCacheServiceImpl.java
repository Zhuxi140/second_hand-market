package com.zhuxi.user.module.application.service.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.utils.JackSonUtils;
import com.zhuxi.common.shared.utils.RedisUtils;
import com.zhuxi.user.module.domain.address.service.UserAddressCacheService;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Service
@Slf4j(topic = "UserAddressCacheServiceImpl")
public class UserAddressCacheServiceImpl implements UserAddressCacheService {

    private final CacheKeyProperties properties;
    private final RedisUtils redisUtils;

    public UserAddressCacheServiceImpl(CacheKeyProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        DefaultRedisScript<Integer> saveInfoScript = new DefaultRedisScript<>();
        DefaultRedisScript<List<Object>> getInfoScript = new DefaultRedisScript<>();
        ClassPathResource savePath = new ClassPathResource("lua/saveInfo.lua");
        ClassPathResource getPath = new ClassPathResource("lua/getInfo.lua");
        ResourceScriptSource getResourceScript = new ResourceScriptSource(getPath);
        ResourceScriptSource saveResourceScript =  new ResourceScriptSource(savePath);
        saveInfoScript.setScriptSource(saveResourceScript);
        saveInfoScript.setResultType(Integer.class);
        getInfoScript.setResultType((Class<List<Object>>)(Class<?>)List.class);
        getInfoScript.setScriptSource(getResourceScript);
    }

    @Override
    public void saveInfo(String userSn, List<UserAddressVO> list){
        String json = JackSonUtils.toJson(list);
        redisUtils.ssSetValue(properties.getAddressInfoKey() + userSn, json,properties.getDefaultInfoExpire(), TimeUnit.SECONDS);
    }

    @Override
    public List<UserAddressVO> getInfo(String userSn) {
        String values = redisUtils.ssGetValue(properties.getAddressInfoKey() + userSn);
        if (values == null){
            return null;
        }
        return JackSonUtils.readValue(values, new TypeReference<List<UserAddressVO>>() {});
    }
}
