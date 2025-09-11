package com.zhuxi.common.properties;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@ConfigurationProperties("jwt")
@Component
@Data
public class JwtProperties {
    private String adminSecret;
    private String userSecret;
    private String adminHeader;
    private String userHeader;
    private Long adminExpire;
    private Long userExpire;

    private SecretKey userSecretKey;
    private SecretKey adminSecretKey;

    public SecretKey getUserSecretKey() {
        if (userSecretKey == null){
            byte[] userKeyBytes = Base64.getDecoder().decode(userSecret);
            userSecretKey = Keys.hmacShaKeyFor(userKeyBytes);
        }
        return userSecretKey;
    }

    public SecretKey getAdminSecretKey() {
        if (adminSecretKey == null){
            byte[] adminKeyBytes = Base64.getDecoder().decode(adminSecret);
            adminSecretKey = Keys.hmacShaKeyFor(adminKeyBytes);
        }
        return adminSecretKey;
    }
}
