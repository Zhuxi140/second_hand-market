package com.zhuxi.common.shared.interceptor;


import com.zhuxi.common.domain.service.PermissionCacheService;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.shared.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhuxi
 */
@Component
@Slf4j
public class JwtInterceptorAdmin implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final PermissionCacheService permissionCacheService;

    public JwtInterceptorAdmin(JwtUtils jwtUtils, PermissionCacheService permissionCacheService) {
        this.jwtUtils = jwtUtils;
        this.permissionCacheService = permissionCacheService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isEmpty()){
            throw new TokenException(AuthMessage.JWT_IS_NULL_OR_EMPTY);
        }

        String token = jwt.replaceAll("(?i)Bearer\\s*", "");

        // 验证是否在黑名单
        boolean result = permissionCacheService.checkBlackToken(token, Role.admin);
        if (result){
            log.warn("admin's JwtToken is block. token:{}", token);
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }

        // 验证令牌
         jwtUtils.validateToken(token, Role.admin);

        return true;
    }
}
