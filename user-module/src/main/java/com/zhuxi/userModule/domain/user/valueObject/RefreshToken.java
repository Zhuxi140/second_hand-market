package com.zhuxi.userModule.domain.user.valueObject;

import com.zhuxi.common.enums.Role;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Getter
public class RefreshToken {
    private final Long id;
    private final Long userId;
    private final String tokenHash;
    private final LocalDateTime expiresAt;
    private final String ipAddress;
    private final String userAgent;
    private final Role role;

    public RefreshToken(Long id, Long userId, String tokenHash, LocalDateTime expiresAt, String ipAddress, String userAgent, Role role) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.role = role;
    }
}
