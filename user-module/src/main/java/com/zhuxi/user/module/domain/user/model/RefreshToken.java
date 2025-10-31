package com.zhuxi.user.module.domain.user.model;

import com.zhuxi.common.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Getter
public class RefreshToken {
    private  Long id;
    private  Long userId;
    private  String tokenHash;
    private  LocalDateTime expiresAt;
    private  String ipAddress;
    private  String userAgent;
    @Setter
    private Role role;

    private RefreshToken(Long id, Long userId, String tokenHash, LocalDateTime expiresAt, String ipAddress, String userAgent) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public RefreshToken() {
    }

    /**
     * 构建令牌
     * @param userId 用户编号
     * @param tokenHash 令牌哈希值
     * @param expiresAt 令牌过期时间
     */
    public void buildToken(Long userId, String tokenHash, LocalDateTime expiresAt){
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
/*        this.ipAddress = ipAddress;
        this.userAgent = userAgent;*/
    }
}
