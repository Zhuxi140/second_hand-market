package com.zhuxi.user.module.infrastructure.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * @author zhuxi
 */
@ConfigurationProperties(prefix = "default")
@Getter
public class DefaultProperties {

    private final String defaultUserAvatar;
    private final int refreshExpire;
    private final String userInfoKey;
    private final Long userInfoExpire;

    @ConstructorBinding
    public DefaultProperties(String defaultUserAvatar, int refreshExpire, String userInfoKey, Long userInfoExpire) {
        this.defaultUserAvatar = defaultUserAvatar;
        this.refreshExpire = refreshExpire;
        this.userInfoKey = userInfoKey;
        this.userInfoExpire = userInfoExpire;
    }
}
