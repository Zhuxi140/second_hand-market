package com.zhuxi.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author zhuxi
 */
@ComponentScan("com.zhuxi")
@MapperScan({
        "com.zhuxi.userModule.infrastructure.mapper",
        "com.zhuxi.orderModule.infrastructure.mapper",
        "com.zhuxi.productModule.infrastructure.mapper",
        "com.zhuxi.promotionModule.infrastructure.mapper"
})
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
