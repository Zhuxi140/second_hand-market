package com.zhuxi.common.utils;

import com.zhuxi.common.properties.BCryptProperties;
import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;

@Component
public class BCryptUtils {

    private final BCryptProperties bCryptProperties;

    public BCryptUtils(BCryptProperties bCryptProperties) {
        this.bCryptProperties = bCryptProperties;
    }

    /**
     * 对密码进行加密
     * @param rawPassword 原密码
     * @return 加密后的密码
     */
    public String hashCode(String rawPassword){
       return BCrypt.hashpw(rawPassword,BCrypt.gensalt(bCryptProperties.getStrength()));
    }

    /**
     * 对密码进行验证
     * @param rawPassword 需比对的密码
     * @param hashPassword 加密后的密码
     * @return 验证结果
     */
    public boolean checkPw(String rawPassword,String hashPassword){
        return BCrypt.checkpw(rawPassword,hashPassword);
    }
}
