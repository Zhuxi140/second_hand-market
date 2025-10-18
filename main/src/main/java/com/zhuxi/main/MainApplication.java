package com.zhuxi.main;

import com.zhuxi.fileModule.config.QCloudConfig;
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
        "com.zhuxi.usermodule.infrastructure.mapper",
        "com.zhuxi.orderModule.infrastructure.mapper",
        "com.zhuxi.productmodule.infrastructure.mapper",
        "com.zhuxi.promotionModule.infrastructure.mapper",
        "com.zhuxi.fileModule.infrastructure.mapper",
})
@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = {
        QCloudConfig.class
})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
