package com.zhuxi.common.infrastructure.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * @author zhuxi
 */
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtProperties {

    private String adminSecret;
    private String userSecret;
    private String adminHeader;
    private String userHeader;
    private Long adminExpire;
    private Long userExpire;
    @JsonIgnore
    private transient SecretKey userSecretKey;
    @JsonIgnore
    private transient SecretKey adminSecretKey;

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

    public Long getAdminExpire() {
        return adminExpire;
    }

    public Long getUserExpire() {
        return userExpire;
    }

    public void setAdminSecret(String adminSecret) {
        this.adminSecret = adminSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public void setAdminHeader(String adminHeader) {
        this.adminHeader = adminHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public void setAdminExpire(Long adminExpire) {
        this.adminExpire = adminExpire;
    }

    public void setUserExpire(Long userExpire) {
        this.userExpire = userExpire;
    }
}
