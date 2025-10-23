package com.zhuxi.main;

import com.zhuxi.common.infrastructure.config.RedisConfig;
import com.zhuxi.fileModule.infrastructure.config.QCloudConfig;
import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuxi
 */
@ComponentScan("com.zhuxi")
@MapperScan({
        "com.zhuxi.**.infrastructure.mapper"
})
@SpringBootApplication
@ConfigurationPropertiesScan(
        basePackages = {
                "com.zhuxi.**.infrastructure.properties",
                "com.zhuxi.**.infrastructure.config"
        }
)
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
