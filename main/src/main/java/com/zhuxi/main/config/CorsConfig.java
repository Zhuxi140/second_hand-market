package com.zhuxi.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import java.util.List;

/**
 * 为什么集中配置：避免控制器随意放开 * 导致安全问题。
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(List.of("https://your-frontend.com")); // 白名单，避免任意来源
        c.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        c.setAllowedHeaders(List.of("Authorization","Content-Type"));
        c.setAllowCredentials(true); // 需要携带 Cookie/凭证时开启
        UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
        s.registerCorsConfiguration("/**", c);
        return s;
    }
}
