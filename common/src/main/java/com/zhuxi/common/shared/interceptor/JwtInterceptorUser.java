package com.zhuxi.common.shared.interceptor;

import com.zhuxi.common.domain.service.PermissionCacheService;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.safe.TokenException;
import com.zhuxi.common.shared.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhuxi
 */
@Slf4j
@Component
public class JwtInterceptorUser implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final PermissionCacheService permissionCacheService;

    public JwtInterceptorUser(JwtUtils jwtUtils, PermissionCacheService permissionCacheService) {
        this.jwtUtils = jwtUtils;
        this.permissionCacheService = permissionCacheService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isBlank()){
            throw new TokenException(AuthMessage.JWT_IS_NULL_OR_EMPTY);
        }

        String token = jwt.replaceAll("(?i)Bearer\\s*", "");

        // 验证是否在黑名单中
        boolean result = permissionCacheService.checkBlackToken(token,Role.user);
        if (result){
            log.warn("user's JwtToken is block. token:{}", token);
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }

        // 验证令牌
        jwtUtils.validateToken(token, Role.user);

        return true;
    }
}
