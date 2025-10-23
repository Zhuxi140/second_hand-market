package com.zhuxi.fileModule.infrastructure.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


/**
 * @author zhuxi
 */
@ConfigurationProperties(prefix = "qcloud")
@Getter
public class QCloudConfig {

    private final String region;
    private final String bucketName;
    private final String SecretId;
    private final String SecretKey;
    private final int expireTime;
    private final int imageSize;
    private final int videoSize;
    private final String baseUrl;
    private final int viewExpireTime;

    @ConstructorBinding
    public QCloudConfig(String region, String bucketName, String secretId, String secretKey, int expireTime, int imageSize, int videoSize, String baseUrl, int viewExpireTime) {
        this.region = region;
        this.bucketName = bucketName;
        SecretId = secretId;
        SecretKey = secretKey;
        this.expireTime = expireTime;
        this.imageSize = imageSize;
        this.videoSize = videoSize;
        this.baseUrl = baseUrl;
        this.viewExpireTime = viewExpireTime;
    }
}
