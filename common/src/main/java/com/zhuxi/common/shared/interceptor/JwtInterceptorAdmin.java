package com.zhuxi.common.shared.interceptor;


import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.shared.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhuxi
 */
@Component
public class JwtInterceptorAdmin implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtInterceptorAdmin(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isEmpty()){
            throw new TokenException(AuthMessage.JWT_IS_NULL_OR_EMPTY);
        }

        String token = jwt.replaceAll("(?i)Bearer\\s*", "");
        // 验证令牌
         jwtUtils.validateToken(token, Role.admin);

        return true;
    }
}
