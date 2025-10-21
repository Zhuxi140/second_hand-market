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

    @ConstructorBinding
    public DefaultProperties(String defaultUserAvatar, int refreshExpire) {
        this.defaultUserAvatar = defaultUserAvatar;
        this.refreshExpire = refreshExpire;
    }
}
