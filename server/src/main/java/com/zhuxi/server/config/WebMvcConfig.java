//package com.zhuxi.server.config;
//
//import com.zhuxi.server.interceptor.JwtInterceptorAdmin;
//import com.zhuxi.server.interceptor.JwtInterceptorUser;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private final JwtInterceptorUser jwtInterceptorUser;
//    private final JwtInterceptorAdmin jwtInterceptorAdmin;
//
//    public WebMvcConfig(JwtInterceptorUser jwtInterceptorUser, JwtInterceptorAdmin jwtInterceptorAdmin) {
//        this.jwtInterceptorUser = jwtInterceptorUser;
//        this.jwtInterceptorAdmin = jwtInterceptorAdmin;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptorUser)
//                .excludePathPatterns("/**/login","/**/register")
//                .addPathPatterns("/user/**");
//
//        registry.addInterceptor(jwtInterceptorAdmin)
//                .excludePathPatterns("/**/login","/**/register")
//                .addPathPatterns("/admin/**");
//    }
//}
