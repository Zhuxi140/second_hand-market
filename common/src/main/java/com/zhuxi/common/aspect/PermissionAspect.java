package com.zhuxi.common.aspect;

import com.zhuxi.common.annotation.PermissionCheck;
import com.zhuxi.common.constant.AuthMessage;
import com.zhuxi.common.enums.Role;
import com.zhuxi.common.exception.TokenException;
import com.zhuxi.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhuxi
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PermissionAspect {

    private final JwtUtils jwtUtils;

    @Around("@annotation(permissionCheck)")
   public Object checkPermission(ProceedingJoinPoint joinPoint, PermissionCheck permissionCheck) throws Throwable {

        HttpServletRequest request = getRequest();
        if (request == null) {
            log.error("checkPermission:getRequest()-error : request is null.");
            throw new TokenException(AuthMessage.REQUEST_CONTEXT_NOT_FOUND);
        }

        Claims claims = getTokenClaims(request);
        Role tokenRole = Role.valueOf((String)claims.get("role"));

        // 效验角色
        log.debug("CheckRole starting");
        boolean result1 = checkRole(tokenRole, permissionCheck.Role(), permissionCheck.disableOverLevel());

        // 效验数据所有者
        if (permissionCheck.enableDataOwnership()) {
            boolean result2 = checkSn(claims,joinPoint,permissionCheck);
            log.debug("Check end");
            return result1 && result2 ? joinPoint.proceed() : false;
        }

        log.debug("Check end");
        return joinPoint.proceed();
    }

   private boolean checkRole(Role toKenRole,Role requiredRole,boolean disableOverLevel){
       int tokenRoleLevel = toKenRole.getLevel();
       int requiredLevel = requiredRole.getLevel();
       // 禁止自动继承
       if (disableOverLevel){
           if(toKenRole.equals(requiredRole)){
               return true;
           }
           log.warn("checkRole:getRoleLevel()-error :tokenRoleLevel is not equal to requiredRole.");
           throw new TokenException(AuthMessage.NO_PERMISSION);
       }

       // 允许自动继承
       if(toKenRole.equals(requiredRole) || tokenRoleLevel > requiredLevel){
           return true;
       }
       log.error("""
       checkRole:getRoleLevel()-error :tokenRoleLevel is not equal to requiredRole.
       or
       tokenRoleLevel less than requiredRoleLevel.
       """);
       throw new TokenException(AuthMessage.NO_PERMISSION);
   }

   private boolean checkSn(Claims claims,ProceedingJoinPoint joinPoint, PermissionCheck permissionCheck){

       String sn = null;
       if ("user".equals(permissionCheck.toRole())) {
           sn = (String) claims.get("userSn");
       }else if("admin".equals(permissionCheck.toRole())){
           sn = (String) claims.get("adminSn");
       }

       String argsUserSn = getArgsUserSn(joinPoint, permissionCheck.snParam());

       if (argsUserSn == null || sn ==  null){
           log.warn("checkSn:getRoleLevel()-warn : argsUserSn:{} or tokenUserSn:{} is null.", argsUserSn, sn);
           throw new TokenException(AuthMessage.NO_PERMISSION);
       }
       log.debug("CheckSn starting");
       if(sn.equals(argsUserSn)){
           return true;
       }
       log.error("checkSn:getRoleLevel()-error : tokenUserSn is not equal to argsUserSn.");
       throw new TokenException(AuthMessage.NO_PERMISSION);
   }


   private HttpServletRequest getRequest(){
       ServletRequestAttributes rA = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       return rA == null ? null : rA.getRequest();
   }

   private Claims getTokenClaims(HttpServletRequest request){
       String auth = request.getHeader("Authorization");
       if (auth == null || auth.isBlank()){
           throw new TokenException(AuthMessage.LOGIN_INVALID);
       }
       String token = auth.replaceAll("(?i)Bearer\\s*", "");
       Claims claims = jwtUtils.parseUserToken(token);
       if (claims == null){
           log.error("getTokenClaims:getRoleLevel()-error : claims is null.");
           throw new TokenException(AuthMessage.LOGIN_INVALID);
       }
       return claims;
   }

   private String getArgsUserSn(ProceedingJoinPoint joinPoint,String paramName){
       Object[] args = joinPoint.getArgs();
       MethodSignature signature = (MethodSignature)joinPoint.getSignature();
       String[] parameterNames = signature.getParameterNames();
       for (int i = 0; i < parameterNames.length; i++){
           if (parameterNames[i].equals(paramName) && args[i] instanceof String){
               return (String)args[i];
           }
       }
       return null;
   }
}
