package com.zhuxi.common.infrastructure.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@ConfigurationProperties(prefix = "bcrypt")
@Data
@Component
public class BCryptProperties {
    private int strength;
}
