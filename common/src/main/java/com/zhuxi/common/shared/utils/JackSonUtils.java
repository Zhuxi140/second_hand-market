package com.zhuxi.common.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@Slf4j(topic = "JackSonUtils")
@Component
public class JackSonUtils {

    public static ObjectMapper objectMapper;

    @Autowired
    public JackSonUtils(ObjectMapper objectMapper) {
        JackSonUtils.objectMapper = objectMapper;
    }

    /**
     * JSON解析
     * @param json json
     * @param clazz 目标对象类
     * @return 目标对象
     * @param <T> 目标对象类型
     */
    public static  <T> T readValue(String json, TypeReference<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析错误", e);
        }
    }

    /**
     * 对象转换
     *
     * @param obj 源对象
     * @param clazz 目标对象类
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    public static <T> T convert(Object obj, Class<T> clazz) {
        try {
            return objectMapper.convertValue(obj, clazz);
        } catch (Exception e) {
            throw new RuntimeException("对象转换错误", e);
        }
    }

    // 将对象转换为json
    public static String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象转换json错误");
            throw new RuntimeException(e);
        }
    }
}
