package com.zhuxi.common.infrastructure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuxi
 */

@ConfigurationProperties(prefix = "cache.key")
@Data
public class CacheKeyProperties {
    private String userPermissionKey;
    private String adminPermissionKey;
    private String blockUserTokenKey;
    private String blockAdminTokenKey;
}
