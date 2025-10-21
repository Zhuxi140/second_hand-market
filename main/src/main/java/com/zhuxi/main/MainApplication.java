package com.zhuxi.main;

import com.zhuxi.fileModule.config.QCloudConfig;
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
        "com.zhuxi.user.module.infrastructure.mapper",
        "com.zhuxi.orderModule.infrastructure.mapper",
        "com.zhuxi.product.module.infrastructure.mapper",
        "com.zhuxi.promotionmodule.infrastructure.mapper",
        "com.zhuxi.fileModule.infrastructure.mapper",
})
@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = {
        QCloudConfig.class,
        DefaultProperties.class
})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
