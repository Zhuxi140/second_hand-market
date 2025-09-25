package com.zhuxi.common.interceptor;

import com.zhuxi.common.constant.JwtMessage;
import com.zhuxi.common.exception.JwtException;
import com.zhuxi.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptorUser implements HandlerInterceptor {
    private final JwtUtils jwtUtils;

    public JwtInterceptorUser(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isEmpty()){
            throw new JwtException(JwtMessage.JWT_IS_NULL_OR_EMPTY);
        }

        String token = jwt.replaceAll("(?i)Bearer\\s*", "");
        if (!jwtUtils.validateToken(token)){
            throw new JwtException(JwtMessage.JWT_IS_INVALID);
        }

        return true;
    }
}
