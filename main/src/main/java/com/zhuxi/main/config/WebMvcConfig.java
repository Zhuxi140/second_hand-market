package com.zhuxi.main.config;

import com.zhuxi.common.shared.interceptor.JwtInterceptorAdmin;
import com.zhuxi.common.shared.interceptor.JwtInterceptorUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhuxi
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptorUser jwtInterceptorUser;
    private final JwtInterceptorAdmin jwtInterceptorAdmin;

    public WebMvcConfig(JwtInterceptorUser jwtInterceptorUser, JwtInterceptorAdmin jwtInterceptorAdmin) {
        this.jwtInterceptorUser = jwtInterceptorUser;
        this.jwtInterceptorAdmin = jwtInterceptorAdmin;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptorUser)
                .excludePathPatterns("/**/login","/**/register","/user/refresh")
                .addPathPatterns("/user/**");

        registry.addInterceptor(jwtInterceptorAdmin)
                .excludePathPatterns("/**/login","/**/register")
                .addPathPatterns("/admin/**");
    }
}
