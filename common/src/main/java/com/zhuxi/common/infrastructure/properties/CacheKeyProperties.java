package com.zhuxi.common.infrastructure.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * @author zhuxi
 */

@ConfigurationProperties(prefix = "cache.key")
@Getter
public class CacheKeyProperties {

    // 权限/安全相关key
    private final String userPermissionKey;
    private final String adminPermissionKey;
    private final String blockUserTokenKey;
    private final String blockAdminTokenKey;
    private final int refreshExpire;
    private final String NULL_VALUE = "NULL_VALUE";

    // 用户相关key
    private final String useridMapSnKey;
    private final String defaultUserAvatar;
    private final String userInfoKey;
    private final Long defaultInfoExpire;

    // 地址相关key
    private final String addressInfoKey;

    // 商品相关key
    private final String categoryKey;
    private final String categoryLockKey;
    private final String conditionKey;
    private final String conditionLockKey;
    private final String shProductKey;
    private final Long shProductExpire;
    private final String meProductKey;
    private final String productStaticKey;


    @ConstructorBinding
    public CacheKeyProperties(String userPermissionKey, String adminPermissionKey, String blockUserTokenKey, String blockAdminTokenKey, int refreshExpire, String useridMapSnKey, String defaultUserAvatar, String userInfoKey, Long defaultInfoExpire, String addressInfoKey, String categoryKey, String categoryLockKey, String conditionKey, String conditionLockKey, String shProductKey, Long shProductExpire, String meProductKey, String productStaticKey) {
        this.userPermissionKey = userPermissionKey;
        this.adminPermissionKey = adminPermissionKey;
        this.blockUserTokenKey = blockUserTokenKey;
        this.blockAdminTokenKey = blockAdminTokenKey;
        this.refreshExpire = refreshExpire;
        this.useridMapSnKey = useridMapSnKey;
        this.defaultUserAvatar = defaultUserAvatar;
        this.userInfoKey = userInfoKey;
        this.defaultInfoExpire = defaultInfoExpire;
        this.addressInfoKey = addressInfoKey;
        this.categoryKey = categoryKey;
        this.categoryLockKey = categoryLockKey;
        this.conditionKey = conditionKey;
        this.conditionLockKey = conditionLockKey;
        this.shProductKey = shProductKey;
        this.shProductExpire = shProductExpire;
        this.meProductKey = meProductKey;
        this.productStaticKey = productStaticKey;
    }
}
