package com.zhuxi.common.shared.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@Component
public class JackSonUtils {

    public static ObjectMapper objectMapper;

    @Autowired
    public JackSonUtils(ObjectMapper objectMapper) {
        JackSonUtils.objectMapper = objectMapper;
    }

    public static  <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析错误", e);
        }
    }

    public static <T> T convert(Object obj, Class<T> clazz) {
        try {
            return objectMapper.convertValue(obj, clazz);
        } catch (Exception e) {
            throw new RuntimeException("对象转换错误", e);
        }
    }
}
